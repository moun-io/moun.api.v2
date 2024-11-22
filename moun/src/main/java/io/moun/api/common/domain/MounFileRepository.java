package io.moun.api.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MounFileRepository extends JpaRepository<MounFile, Long> {
    Optional<MounFile> findMounFileById(Long id);

    Optional<MounFile> findMounFileByFileName(String fileName);
}
