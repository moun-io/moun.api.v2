package io.moun.api.song.domain;

import io.moun.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {


    List<Song> findAllByMember(Member member);
    List<Song> findSongsBySongGenresAndSongVibes(GenreType genre, VibeType vibe);
}
