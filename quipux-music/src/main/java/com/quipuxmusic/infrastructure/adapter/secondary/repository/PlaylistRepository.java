package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.entities.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
    
    Optional<PlaylistEntity> findByName(String name);
    
    boolean existsByName(String name);
    
    void deleteByName(String name);
} 