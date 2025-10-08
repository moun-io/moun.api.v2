package io.moun.api.memberauction;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAuctionRelationRepository extends JpaRepository<MemberAuctionRelation, Long> {
}
