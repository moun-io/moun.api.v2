package io.moun.api.auction.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAuctionsByOrderByEndDateAsc();
}
