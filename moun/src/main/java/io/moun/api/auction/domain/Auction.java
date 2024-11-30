package io.moun.api.auction.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Temporal(TemporalType.TIMESTAMP) -> 자바 8이후로는 LocalDate / LocalDateTime 사용
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    
    @NotNull
    private int startBidAmount;
    @NotNull
    private int winningBidAmount;
    
    @NotNull
    private boolean isCopyrightTransfer;
    @NotNull
    private boolean isExpired;

    @Builder
    public Auction(LocalDate startDate, LocalDate endDate, int startBidAmount, int winningBidAmount, boolean isCopyrightTransfer, boolean isExpired) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startBidAmount = startBidAmount;
        this.winningBidAmount = winningBidAmount;
        this.isCopyrightTransfer = isCopyrightTransfer;
        this.isExpired = isExpired;
    }
}
