package io.moun.api.song.controller;

import io.moun.api.common.controller.dto.MounFileUploadResponse;
import io.moun.api.song.controller.dto.SongAuctionVO;
import io.moun.api.song.domain.Song;
import io.moun.api.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping("/songs/files") //Create
    public ResponseEntity<MounFileUploadResponse> uploadSongFiles(
            @RequestPart(value = "song-file") MultipartFile songFile,
            @RequestPart(value = "cover-file") MultipartFile coverFile
    ) {

        return songService.uploadSongRelatedFiles(songFile, coverFile);
    }

    @PostMapping("/songs") //Create
    public ResponseEntity<Song> createSongAndAuction(@RequestBody SongAuctionVO songAuctionVO) {


        return songService.uploadSongAndAuction(songAuctionVO);
    }

    @GetMapping("/songs/{id}") //Read
    public ResponseEntity<List<LinkedMultiValueMap<String, Object>>> getSongInformation(@PathVariable Long id) {

        return songService.findAllSongByMemberId(id);
    }

    @GetMapping("/songs") //Read
    public ResponseEntity<List<LinkedMultiValueMap<String, Object>>> getAllSongs() {
        return songService.findAllSongs();
    }
}
