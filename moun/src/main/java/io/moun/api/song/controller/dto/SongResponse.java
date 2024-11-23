package io.moun.api.song.controller.dto;

import io.moun.api.auction.domain.Auction;
import io.moun.api.common.BaseEntityResponse;
import io.moun.api.common.domain.MounFile;
import io.moun.api.member.domain.Member;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SongResponse extends BaseEntityResponse {

    private Long id;

    private String title;

    private String description;

    private Member member;

    private Auction auction;

    private String songUrl;

    private String coverUrl;

    @Builder
    public SongResponse(Long id, String title, String description, Member member, Auction auction, String songUrl, String coverUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.member = member;
        this.auction = auction;
        this.songUrl = songUrl;
        this.coverUrl = coverUrl;
    }
}