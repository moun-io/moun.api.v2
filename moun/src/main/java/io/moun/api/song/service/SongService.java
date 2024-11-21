package io.moun.api.song.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.time.Duration;
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
    private final ObjectMapper objectMapper;

    //music upload - file / api1
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

    //music upload - dto / api2
    @Transactional
    public ResponseEntity<Song> uploadSongAndAuction(SongAuctionVO songAuctionVO) {

        Long memberId = jwtTokenHelper.getMemberId();

        Member member = memberQueryService.findById(memberId);

        AuctionRequest auctionRequest = songAuctionVO.getAuctionRequest();
        auctionRequest.setExpired(false);
        SongRequest songRequest = songAuctionVO.getSongRequest();

        MounFile songFile = mounFileService.getMounFileById(songRequest.getSongFileId());
        MounFile coverFile = mounFileService.getMounFileById(songRequest.getCoverFileId());

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
                .songFile(songFile)
                .coverImageFile(coverFile)
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
    public ResponseEntity<List<LinkedMultiValueMap<String, Object>>> findAllSongByMemberId(Long id) {

        Member member = memberQueryService.findById(id);

        List<Song> songsByMember = songRepository.findAllByMember(member);

        List<LinkedMultiValueMap<String, Object>> songResponseList = null;
        try {
            songResponseList = getSongResponses(songsByMember);

        } catch (Exception e) {
            throw new RuntimeException("ERRORRORROR");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "multipart/form-data");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(songResponseList);
    }

    //find all songs / api
    public ResponseEntity<List<LinkedMultiValueMap<String, Object>>> findAllSongs() {

        List<Song> all = songRepository.findAll();

        List<LinkedMultiValueMap<String, Object>> songResponses = getSongResponses(all);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "multipart/form-data");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(songResponses);
    }




    //make file local direction to string / method
    private String makeFileDir(String fileName) {
        return mounFileService.LOCAL_UPLOAD_DIR + "/" + fileName;
    }

    //get songs list / method
    private List<LinkedMultiValueMap<String, Object>> getSongResponses(List<Song> songList) {

        List<LinkedMultiValueMap<String, Object>> songResponseList;

        songResponseList = songList.stream()
                .map(song -> {
                    String songFileDir = makeFileDir(song.getSongFile().getFileName());
                    String coverFileDir = makeFileDir(song.getSongFile().getFileName());

                    SongResponse build = SongResponse.builder()
                            .id(song.getId())
                            .title(song.getTitle())
                            .description(song.getDescription())
                            .songVibes(song.getSongVibes())
                            .songGenres(song.getSongGenres())
                            .memberId(song.getMember().getId())
                            .auction(song.getAuction())
                            .createdDate(song.getCreatedDate())
                            .lastModifiedDate(song.getLastModifiedDate())
                            .build();

                    Resource songRes = null;
                    Resource coverRes = null;
                    String meta = null;
                    try {
                        songRes = new UrlResource(Paths.get(songFileDir).toUri());
                        coverRes = new UrlResource(Paths.get(coverFileDir).toUri());

                        meta = objectMapper.writeValueAsString(build);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                    map.add("audio", songRes);
                    map.add("image", coverRes);



                    map.add("metadata", meta);

                    return map;
                })
                .collect(Collectors.toList());

        return songResponseList;
    }
}
