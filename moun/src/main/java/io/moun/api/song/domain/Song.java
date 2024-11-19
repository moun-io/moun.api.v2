package io.moun.api.song.domain;

import io.moun.api.common.BaseEntity;
import io.moun.api.auction.domain.Auction;
import io.moun.api.common.domain.MounFile;
import io.moun.api.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Song extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;
    @NotNull
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
    @OneToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;
    
    @OneToOne
    @JoinColumn(name = "song_file_id")
    private MounFile songFile;

    @OneToOne
    @JoinColumn(name = "cover_image_file_id")
    private MounFile coverImageFile;

    @Builder
    public Song(String title, String description, Member member, Auction auction, MounFile songFile, MounFile coverImageFile) {
        this.title = title;
        this.description = description;
        this.member = member;
        this.auction = auction;
        this.songFile = songFile;
        this.coverImageFile = coverImageFile;
    }
}
