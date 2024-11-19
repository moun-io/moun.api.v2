package io.moun.api.auction.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    
    @NotNull
    private int startBidAmount;
    @NotNull
    private int winningBidAmount;
    
    @NotNull
    private boolean isCopyrightTransfer;
    @NotNull
    private boolean isExpired;
}
