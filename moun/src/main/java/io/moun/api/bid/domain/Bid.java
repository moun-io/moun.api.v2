package io.moun.api.bid.domain;

import io.moun.api.auction.domain.Auction;
import io.moun.api.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberAuctionRelationId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @NotNull
    private LocalDateTime bidDate;
    @NotNull
    private int amount;
}
