package io.moun.api.member.domain.repository;

import io.moun.api.member.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    @Modifying
    @Query("DELETE FROM Position p WHERE p.member.id = :memberId")
    public void deleteAllByMemberId(@Param("memberId") Long memberId);
}
