package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
} 