package io.moun.api.auction.controller;

import io.moun.api.bid.domain.Bid;
import io.moun.api.security.service.IJwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
public class AuctionController {
    private final IJwtTokenHelper jwtTokenHelper;

    @GetMapping("/auctions/{auctionId}/bid")
    public ResponseEntity<List<Bid>> getBid(@PathVariable Long auctionId) {
        throw new NotImplementedException();
    }

    @GetMapping("/auctions/{auctionId}/highest")
    public ResponseEntity<Bid> getHighestBid(@PathVariable Long auctionId) {
        throw new NotImplementedException();
    }

    @PostMapping("/auctions/{auctionId}/bid")
    public ResponseEntity<List<Bid>> createBid(@PathVariable Long auctionId) {
        throw new NotImplementedException();
    }

    @DeleteMapping("/auctions/{auctionId}/bid")
    public ResponseEntity<Boolean> cancelBid(@PathVariable Long auctionId) {
        throw new NotImplementedException();
    }
}
