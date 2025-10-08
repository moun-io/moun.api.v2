package io.moun.api.song.service;

import io.moun.api.auction.controller.dto.AuctionRequest;
import io.moun.api.auction.domain.Auction;
import io.moun.api.auction.domain.AuctionRepository;
import io.moun.api.common.domain.MounFile;
import io.moun.api.common.domain.SortType;
import io.moun.api.common.service.MounFileService;
import io.moun.api.member.domain.Member;
import io.moun.api.member.service.MemberQueryService;
import io.moun.api.security.infrastructure.JwtTokenHelper;
import io.moun.api.song.controller.dto.SongAuctionVO;
import io.moun.api.song.controller.dto.SongRequest;
import io.moun.api.song.controller.dto.SongResponse;
import io.moun.api.song.domain.GenreType;
import io.moun.api.song.domain.Song;
import io.moun.api.song.domain.SongRepository;
import io.moun.api.song.domain.VibeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService {

    private final SongRepository songRepository;
    private final AuctionRepository auctionRepository;
    private final MemberQueryService memberQueryService;
    private final MounFileService mounFileService;
    private final JwtTokenHelper jwtTokenHelper;

    //music upload - file / api1
    @Transactional
    public ResponseEntity<SongResponse> uploadSongRelatedFiles(Long id, MultipartFile songFile, MultipartFile coverFile) {

        MounFile songUpload = mounFileService.uploadFileToLocalAndSave(songFile);
        MounFile coverUpload = mounFileService.uploadFileToLocalAndSave(coverFile);

        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        song.addFile(songUpload, coverUpload);

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

        Set<String> songGenres = songRequest.getSongGenres();
        Set<String> songVibes = songRequest.getSongVibes();

        Set<GenreType> realGenres = new HashSet<>();
        Set<VibeType> realVibes = new HashSet<>();
        for (String songGenre : songGenres) {
            GenreType genreType = GenreType.valueOf(songGenre.toUpperCase());

            if (!songGenre.equalsIgnoreCase(genreType.toString())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            realGenres.add(genreType);
        }
        for (String songVibe: songVibes) {
            VibeType vibeType = VibeType.valueOf(songVibe.toUpperCase());

            if (!songVibe.equalsIgnoreCase(vibeType.toString())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            realVibes.add(vibeType);
        }

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
                .songGenres(realGenres)
                .songVibes(realVibes)
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

    //find all songs by genre & vibe / api
    public ResponseEntity<List<SongResponse>> findSongsByGenreAndVibe(String genreType, String vibeType) {
        if (!genreType.isBlank() || !vibeType.isBlank()) {

            if (Arrays.stream(GenreType.values()).anyMatch(g -> g.name().equalsIgnoreCase(genreType)) &&
                    Arrays.stream(VibeType.values()).anyMatch(v -> v.name().equalsIgnoreCase(vibeType))) {

                GenreType gt = GenreType.valueOf(genreType.toUpperCase());
                VibeType vt = VibeType.valueOf(vibeType.toUpperCase());

                List<Song> songs = songRepository.findSongsBySongGenresAndSongVibes(gt, vt);

                List<SongResponse> songResponses = getSongResponses(songs);

                return ResponseEntity.status(HttpStatus.OK).body(songResponses);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //find all songs by sort type / api
    public ResponseEntity<List<SongResponse>> findSongsBySortType(String sortType) {

        if (!sortType.isBlank()) {
            //later, add sort conditions here...
            if (Arrays.stream(SortType.values()).noneMatch(s -> s.name().equalsIgnoreCase(sortType))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            List<Auction> auctions = auctionRepository.findAuctionsByOrderByEndDateAsc();

            List<SongResponse> responses = new ArrayList<>();
            for (Auction auction : auctions) {
                Song song = songRepository.findById(auction.getId()).orElseThrow(() -> new RuntimeException("Song id by Auction not found"));

                SongResponse build = SongResponse.create(song);

                responses.add(build);
            }

            return ResponseEntity.status(HttpStatus.OK).body(responses);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //download audio multifile / api
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

    //download image multifile / api
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

    //update song, auction entity / api
    @Transactional
    public ResponseEntity<String> updateSong(Long id, SongRequest updateRequest) {
        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song id not found"));

        //genre & vibe
        Set<String> updateGenres = updateRequest.getSongGenres();
        Set<GenreType> originGenres = song.getSongGenres();

        if (!updateGenres.isEmpty()) {
            originGenres = updateGenres.stream()
                    .map(s -> GenreType.valueOf(s.toUpperCase()))
                    .collect(Collectors.toSet());
        }

        Set<String> updateVibes = updateRequest.getSongVibes();
        Set<VibeType> originVibes = song.getSongVibes();

        if (!updateVibes.isEmpty()) {
            originVibes = updateVibes.stream()
                    .map(s ->  VibeType.valueOf(s.toUpperCase()))
                    .collect(Collectors.toSet());
        }

        //title
        String title = updateRequest.getTitle();
        if (title.isBlank()) {
            title = song.getTitle();
        }

        //description
        String description = updateRequest.getDescription();
        if (description.isBlank()) {
            description = song.getDescription();
        }

        song.update(title, description, originGenres, originVibes);

        return ResponseEntity.status(HttpStatus.OK).body("Successful");
    }

    //delete song by id / api
    @Transactional
    public ResponseEntity<String> deleteSongsById(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song id not found"));


        mounFileService.deleteById(song.getSongFile().getId());
        mounFileService.deleteById(song.getCoverImageFile().getId());
        auctionRepository.deleteById(id);
        songRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete successful");
    }


    //get songs list / method
    private List<SongResponse> getSongResponses(List<Song> songList) {

        List<SongResponse> responses = new ArrayList<>();

        for (Song song : songList) {
            String songName = song.getSongFile().getFileName();
            String coverName = song.getCoverImageFile().getFileName();

            SongResponse build = SongResponse.create(song);

            responses.add(build);
        }

        return responses;
    }
}
