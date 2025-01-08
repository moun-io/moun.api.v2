package io.moun.api.auction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Temporal(TemporalType.TIMESTAMP) -> 자바 8이후로는 LocalDate / LocalDateTime 사용
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;
    @NotNull
    @FutureOrPresent
    private LocalDate endDate;
    
    @NotNull
    @PositiveOrZero
    private int startBid;
    @NotNull
    @PositiveOrZero
    private int winningBid;
    
    @NotNull
    private boolean isCopyrightTransfer;
    @NotNull
    private boolean isExpired;

    @Builder
    public Auction(LocalDate startDate, LocalDate endDate, int startBid, int winningBid, boolean isCopyrightTransfer, boolean isExpired) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startBid = startBid;
        this.winningBid = winningBid;
        this.isCopyrightTransfer = isCopyrightTransfer;
        this.isExpired = isExpired;
    }
}
