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
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService {

    private final SongRepository songRepository;
    private final AuctionRepository auctionRepository;
    private final MemberQueryService memberQueryService;
    private final MounFileService mounFileService;
    private final JwtTokenHelper jwtTokenHelper;

    //music upload api1
    @Transactional
    public ResponseEntity<MounFileUploadResponse> uploadSongRelatedFiles(MultipartFile songFile, MultipartFile coverFile) {

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

    //music upload api2
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
                .songGenres(songRequest.getSongGenres())
                .songVibes(songRequest.getSongVibes())
                .build();

        //save into repository
        auctionRepository.save(buildAuction);
        Song savedSong = songRepository.save(buildSong);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
    }

    //find all songs by member id api
    public ResponseEntity<List<SongResponse>> findAllSongByMemberId(Long id) {

        Member member = memberQueryService.findById(id);

        List<Song> songsByMember = songRepository.findAllByMember(member);
        //List<Song> allByMemberId = songRepository.findSongsByMember_Id(id);

        List<SongResponse> songResponseList = null;
        try {
            songResponseList = getSongResponses(songsByMember);

        } catch (Exception e) {
            throw new RuntimeException("ERRORRORROR");
        }

        return ResponseEntity.status(HttpStatus.OK).body(songResponseList);
    }

    //find all songs api
    public ResponseEntity<List<SongResponse>> findAllSongs() {

        List<Song> all = songRepository.findAll();

        List<SongResponse> songResponseList = null;
        try {
            songResponseList = getSongResponses(all);
        } catch (Exception e) {
            throw new RuntimeException("ERRORRORROR");
        }
        return ResponseEntity.status(HttpStatus.OK).body(songResponseList);
    }




    //make file local direction to string method
    private String makeFileDir(String fileName) {
        return mounFileService.LOCAL_UPLOAD_DIR + "/" + fileName;
    }

    //get songs list method
    private List<SongResponse> getSongResponses(List<Song> songList) {

        List<SongResponse> songResponseList;
        songResponseList = songList.stream()
                .map(song -> {
                    String songFileDir = makeFileDir(song.getSongFile().getFileName());
                    String coverFileDir = makeFileDir(song.getSongFile().getFileName());

                    return SongResponse.builder()
                            .id(song.getId())
                            .title(song.getTitle())
                            .description(song.getDescription())
                            .songVibes(song.getSongVibes())
                            .songGenres(song.getSongGenres())
                            .memberId(song.getMember().getId())
                            .auction(song.getAuction())
                            .songUrl(songFileDir)
                            .coverUrl(coverFileDir)
                            .createdDate(song.getCreatedDate())
                            .lastModifiedDate(song.getLastModifiedDate())
                            .build();
                })
                .collect(Collectors.toList());
        return songResponseList;
    }
}
