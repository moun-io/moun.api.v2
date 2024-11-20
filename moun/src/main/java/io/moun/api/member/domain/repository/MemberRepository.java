package io.moun.api.member.domain.repository;

import io.moun.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.positions p WHERE m.id = :memberId")
    Optional<Member> findMemberWithPositionById(@Param("memberId") Long memberId);
}
