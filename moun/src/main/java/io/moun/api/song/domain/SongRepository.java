package io.moun.api.song.domain;

import io.moun.api.member.domain.Member;
import io.moun.api.song.controller.dto.SongResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {


    List<Song> findAllByMember(Member member);
}
