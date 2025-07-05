package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    
    Optional<Playlist> findByName(String name);
    
    boolean existsByName(String name);
    
    void deleteByName(String name);
} 