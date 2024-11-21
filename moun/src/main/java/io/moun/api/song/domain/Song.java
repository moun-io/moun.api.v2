package io.moun.api.song.domain;

import io.moun.api.auction.domain.Auction;
import io.moun.api.common.BaseEntity;
import io.moun.api.common.domain.MounFile;
import io.moun.api.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    @ElementCollection(targetClass = GenreType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "song_genres", joinColumns = @JoinColumn(name = "song_id"))
    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<GenreType> songGenres = new HashSet<>();

    @ElementCollection(targetClass = VibeType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "song_vibes", joinColumns = @JoinColumn(name = "song_id"))
    @Column(name = "vibe", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<VibeType> songVibes = new HashSet<>();

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
    public Song(String title, String description, Set<GenreType> songGenres, Set<VibeType> songVibes,  Member member, Auction auction, MounFile songFile, MounFile coverImageFile) {
        this.title = title;
        this.description = description;
        this.songGenres = songGenres;
        this.songVibes = songVibes;
        this.member = member;
        this.auction = auction;
        this.songFile = songFile;
        this.coverImageFile = coverImageFile;
    }
}
