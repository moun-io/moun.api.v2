package io.moun.api.song.service;

import io.moun.api.auction.controller.dto.AuctionRequest;
import io.moun.api.auction.domain.Auction;
import io.moun.api.auction.domain.AuctionRepository;
import io.moun.api.common.controller.dto.MounFileUploadResponse;
import io.moun.api.common.domain.MounFile;
import io.moun.api.common.service.MounFileService;
import io.moun.api.member.domain.Member;
import io.moun.api.member.service.MemberQueryService;
import io.moun.api.security.infrastructure.JwtTokenHelper;
import io.moun.api.song.controller.dto.SongAuctionVO;
import io.moun.api.song.controller.dto.SongRequest;
import io.moun.api.song.controller.dto.SongResponse;
import io.moun.api.song.domain.Song;
import io.moun.api.song.domain.SongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final AuctionRepository auctionRepository;
    private final MemberQueryService memberQueryService;
    private final MounFileService mounFileService;
    private final StringHttpMessageConverter stringHttpMessageConverter;
    private final JwtTokenHelper jwtTokenHelper;

    @Transactional
    public ResponseEntity<MounFileUploadResponse> uploadSongRelatedFiles(MultipartFile songFile,
                                                         MultipartFile coverFile) {

        MounFile songUpload = mounFileService.uploadFileToLocalAndSave(songFile);
        MounFile coverUpload = mounFileService.uploadFileToLocalAndSave(coverFile);

        MounFileUploadResponse mfup = new MounFileUploadResponse(
                songUpload.getId(),
                songUpload.getFileName(),
                coverUpload.getId(),
                coverUpload.getFileName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(mfup);
    }

    @Transactional
    public ResponseEntity<Song> uploadSongAndAuction(SongAuctionVO songAuctionVO) {

        Long memberId = jwtTokenHelper.getMemberId();

        Member member = memberQueryService.findById(memberId);

        AuctionRequest auctionRequest = songAuctionVO.getAuctionRequest();
        auctionRequest.setExpired(false);
        SongRequest songRequest = songAuctionVO.getSongRequest();

        MounFile songFile = mounFileService.getMounFileById(songRequest.getSongFileId());
        MounFile coverFile = mounFileService.getMounFileById(songRequest.getCoverFileId());

        Auction buildAuction = Auction.builder()
                .startDate(auctionRequest.getStartDate())
                .endDate(auctionRequest.getEndDate())
                .startBid(auctionRequest.getStartBid())
                .winningBid(auctionRequest.getWinningBid())
                .isCopyrightTransfer(auctionRequest.isCopyrightTransfer())
                .isExpired(auctionRequest.isExpired())
                .build();

        Song buildSong = Song.builder()
                .member(member)
                .auction(buildAuction)
                .songFile(songFile)
                .coverImageFile(coverFile)
                .title(songRequest.getTitle())
                .description(songRequest.getDescription())
                .build();

        //save into repository
        auctionRepository.save(buildAuction);
        Song savedSong = songRepository.save(buildSong);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
    }
    
    
    public ResponseEntity<List<SongResponse>> findAllSongByMemberId(Long id) {
        
        Member member = memberQueryService.findById(id);
        
        List<Song> songsByMember = songRepository.findAllByMember(member);

        List<SongResponse> songResponseList = null;
        try {
            songResponseList = songsByMember.stream()
                    .map(song -> {
                        String songFileDir = mounFileService.LOCAL_UPLOAD_DIR + "/" + song.getSongFile().getFileName();
                        String coverFileDir = mounFileService.LOCAL_UPLOAD_DIR + "/" + song.getCoverImageFile().getFileName();

                        return SongResponse.builder()
                            .id(song.getId())
                            .title(song.getTitle())
                            .description(song.getDescription())
                            .member(member)
                            .auction(song.getAuction())
                            .songUrl(songFileDir)
                            .coverUrl(coverFileDir)
                            .build();})
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("TQQQQQQQQQQQQQQQQ");
        }

        return ResponseEntity.status(HttpStatus.OK).body(songResponseList);
    }
}
