package io.moun.api.song.controller.dto;

import io.moun.api.auction.domain.Auction;
import io.moun.api.common.BaseEntityResponse;
import io.moun.api.song.domain.GenreType;
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

    private Long memberId;

    private Auction auction;

    private String songUrl;

    private String coverUrl;

    @Builder
    public SongResponse(LocalDateTime createdDate, LocalDateTime lastModifiedDate, Long id, String title, String description, Set<GenreType> songGenres, Set<VibeType> songVibes, Long memberId, Auction auction, String songUrl, String coverUrl) {
        super(createdDate, lastModifiedDate);
        this.id = id;
        this.title = title;
        this.description = description;
        this.songGenres = songGenres;
        this.songVibes = songVibes;
        this.memberId = memberId;
        this.auction = auction;
        this.songUrl = songUrl;
        this.coverUrl = coverUrl;
    }


}