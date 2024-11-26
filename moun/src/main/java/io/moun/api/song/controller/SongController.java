package io.moun.api.song.controller;

import io.moun.api.song.controller.dto.SongAuctionVO;
import io.moun.api.song.controller.dto.SongRequest;
import io.moun.api.song.controller.dto.SongResponse;
import io.moun.api.song.domain.Song;
import io.moun.api.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PatchMapping("/songs/{id}/files") //upload - update
    public ResponseEntity<SongResponse> uploadSongFiles(
            @PathVariable Long id,
            @RequestPart(value = "song-file") MultipartFile songFile,
            @RequestPart(value = "cover-file") MultipartFile coverFile
    ) {

        return songService.uploadSongRelatedFiles(id, songFile, coverFile);
    }

    @PostMapping("/songs") //make song entity - create
    public ResponseEntity<Song> createSongAndAuction(@RequestBody SongAuctionVO songAuctionVO) {


        return songService.uploadSongAndAuction(songAuctionVO);
    }

    @GetMapping("/songs/members/{id}") //find songs by member id - read
    public ResponseEntity<List<SongResponse>> getSongInformationByMember(@PathVariable Long id) {

        return songService.findAllSongByMemberId(id);
    }

    @GetMapping("/songs") // find all songs / by genre & vibe / sortby - read
    public ResponseEntity<List<SongResponse>> getAllSongs(@RequestParam(name = "genre", required = false) String genre,
                                                          @RequestParam(name = "vibe", required = false) String vibe,
                                                          @RequestParam(name = "sort", required = false) String sortType) {

        // 모든 필터가 없는 경우
        if (genre == null && vibe == null && sortType == null) {
            return songService.findAllSongs();
        }

        // 정렬만 요청된 경우
        if (genre == null && vibe == null && !sortType.isEmpty()) {
            return songService.findSongsBySortType(sortType);
        }

        // 장르와 분위기 요청된 경우 (정렬 포함 가능)
        return songService.findSongsByGenreAndVibe(genre, vibe);
    }

    @GetMapping("/songs/{id}/audio") //download song file - read
    public ResponseEntity<byte[]> downloadSongFileBySongId(@PathVariable Long id) throws IOException {
        return songService.downloadSongFileBySongId(id);
    }

    @GetMapping("/songs/{id}/image") //download image file - read
    public ResponseEntity<byte[]> downloadImageFileBySongId(@PathVariable Long id) throws IOException {
        return songService.downloadImageFileBySongId(id);
    }

    @PatchMapping("/songs/{id}")
    public ResponseEntity<String> updateSongEntity(@PathVariable Long id, @RequestBody SongRequest request) {
        return songService.updateSong(id, request);
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<String> deleteSongById(@PathVariable Long id) {
        return songService.deleteSongsById(id);
    }
}
