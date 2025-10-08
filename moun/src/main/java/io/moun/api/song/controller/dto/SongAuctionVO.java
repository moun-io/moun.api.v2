package io.moun.api.song.controller.dto;

import io.moun.api.auction.controller.dto.AuctionRequest;
import io.moun.api.auction.domain.Auction;
import lombok.Data;
import lombok.Getter;

@Data
public class SongAuctionVO {
    private SongRequest songRequest;
    private AuctionRequest auctionRequest;
}
