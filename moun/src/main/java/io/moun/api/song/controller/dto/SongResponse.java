package io.moun.api.song.controller.dto;

import io.moun.api.auction.domain.Auction;
import io.moun.api.common.BaseEntityResponse;
import io.moun.api.common.domain.StaticVariables;
import io.moun.api.song.domain.GenreType;
import io.moun.api.song.domain.Song;
import io.moun.api.song.domain.VibeType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class SongResponse extends BaseEntityResponse {

    private Long id;

    private String title;

    private String description;

    private Set<GenreType> songGenres;

    private Set<VibeType> songVibes;

    private String songFileURL;

    private String coverFileURL;

    private Long memberId;

    private Auction auction;

    @Builder
    public SongResponse(LocalDateTime createdDate, LocalDateTime lastModifiedDate, Long id, String title, String description, Set<GenreType> songGenres, Set<VibeType> songVibes, String songFileURL, String coverFileURL, Long memberId, Auction auction) {
        super(createdDate, lastModifiedDate);
        this.id = id;
        this.title = title;
        this.description = description;
        this.songGenres = songGenres;
        this.songVibes = songVibes;
        this.songFileURL = songFileURL;
        this.coverFileURL = coverFileURL;
        this.memberId = memberId;
        this.auction = auction;
    }

    public static SongResponse create(Song song) {
         return SongResponse.builder()
                .id(song.getId())
                .title(song.getTitle())
                .description(song.getDescription())
                .songGenres(song.getSongGenres())
                .songVibes(song.getSongVibes())
                .songFileURL(StaticVariables.getURL + "audio")
                .coverFileURL(StaticVariables.getURL + "image")
                .createdDate(song.getCreatedDate())
                .lastModifiedDate(song.getLastModifiedDate())
                .memberId(song.getMember().getId())
                .auction(song.getAuction())
                .build();
    }
}