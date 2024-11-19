package io.moun.api.auction.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuctionRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private int startBid;
    private int winningBid;
    private boolean isCopyrightTransfer;
    private boolean isExpired;
}
