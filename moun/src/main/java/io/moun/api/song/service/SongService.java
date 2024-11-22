package io.moun.api.song.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.moun.api.auction.controller.dto.AuctionRequest;
import io.moun.api.auction.domain.Auction;
import io.moun.api.auction.domain.AuctionRepository;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService {

    private final SongRepository songRepository;
    private final AuctionRepository auctionRepository;
    private final MemberQueryService memberQueryService;
    private final MounFileService mounFileService;
    private final JwtTokenHelper jwtTokenHelper;
    private final ObjectMapper objectMapper;

    //music upload - file / api1
    @Transactional
    public ResponseEntity<SongResponse> uploadSongRelatedFiles(Long id, MultipartFile songFile, MultipartFile coverFile) {

        MounFile songUpload = mounFileService.uploadFileToLocalAndSave(songFile);
        MounFile coverUpload = mounFileService.uploadFileToLocalAndSave(coverFile);

        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        song.update(songUpload, coverUpload);

        SongResponse build = SongResponse.builder()
                .id(song.getId())
                .title(song.getTitle())
                .description(song.getDescription())
                .songVibes(song.getSongVibes())
                .songGenres(song.getSongGenres())
                .songFileURL(songUpload.getFilePath())
                .coverFileURL(coverUpload.getFilePath())
                .auction(song.getAuction())
                .createdDate(song.getCreatedDate())
                .lastModifiedDate(song.getLastModifiedDate())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(build);
    }

    //music upload - dto / api2
    @Transactional
    public ResponseEntity<Song> uploadSongAndAuction(SongAuctionVO songAuctionVO) {

        Long memberId = jwtTokenHelper.getMemberId();

        Member member = memberQueryService.findById(memberId);

        AuctionRequest auctionRequest = songAuctionVO.getAuctionRequest();
        auctionRequest.setExpired(false);
        SongRequest songRequest = songAuctionVO.getSongRequest();

        //if start date is later than end date, also start bid is smaller than winning bid, return 400 / null
        if (Duration.between(auctionRequest.getStartDate().atStartOfDay(), auctionRequest.getEndDate().atStartOfDay()).toDays() <= 0
                || auctionRequest.getStartBid() - auctionRequest.getWinningBid() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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
                .title(songRequest.getTitle())
                .description(songRequest.getDescription())
                .songGenres(songRequest.getSongGenres())
                .songVibes(songRequest.getSongVibes())
                .build();

        auctionRepository.save(buildAuction);
        Song savedSong = songRepository.save(buildSong);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
    }

    //find all songs by member id / api
    public ResponseEntity<List<SongResponse>> findAllSongByMemberId(Long id) {

        Member member = memberQueryService.findById(id);

        List<Song> songsByMember = songRepository.findAllByMember(member);

        List<SongResponse> songResponseList = getSongResponses(songsByMember);

        return ResponseEntity.status(HttpStatus.OK).body(songResponseList);
    }

    //find all songs / api
    public ResponseEntity<List<SongResponse>> findAllSongs() {

        List<Song> all = songRepository.findAll();

        List<SongResponse> songResponseList = getSongResponses(all);

        return ResponseEntity.status(HttpStatus.OK).body(songResponseList);
    }

    //get audio multifile
    public ResponseEntity<byte[]> downloadSongFileBySongId(Long id) throws IOException {

        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        MounFile songFile = song.getSongFile();
        byte[] bytes = mounFileService.downloadFileFromLocal(songFile.getGeneratedFileName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; " + songFile.getFileName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(songFile.getContentType()))
                .headers(headers)
                .body(bytes);
    }

    //get image multifile
    public ResponseEntity<byte[]> downloadImageFileBySongId(Long id) throws IOException {

        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        MounFile coverImageFile = song.getCoverImageFile();
        byte[] bytes = mounFileService.downloadFileFromLocal(coverImageFile.getGeneratedFileName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; " + coverImageFile.getFileName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(coverImageFile.getContentType()))
                .headers(headers)
                .body(bytes);
    }



    //get songs list / method
    private List<SongResponse> getSongResponses(List<Song> songList) {

        String getURL = "GET /api/songs/{id}/";
        List<SongResponse> responses = new ArrayList<>();

        for (Song song : songList) {
            String songName = song.getSongFile().getFileName();
            String coverName = song.getCoverImageFile().getFileName();

            SongResponse build = SongResponse.builder()
                    .id(song.getId())
                    .title(song.getTitle())
                    .description(song.getDescription())
                    .songGenres(song.getSongGenres())
                    .songVibes(song.getSongVibes())
                    .songFileURL(getURL + "audio")
                    .coverFileURL(getURL + "image")
                    .createdDate(song.getCreatedDate())
                    .lastModifiedDate(song.getLastModifiedDate())
                    .memberId(song.getMember().getId())
                    .auction(song.getAuction())
                    .build();

            responses.add(build);
        }

        return responses;
    }
}
