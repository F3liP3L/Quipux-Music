package com.quipuxmusic.core.domain.port;

import com.quipuxmusic.core.domain.domains.PlaylistDomain;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepositoryPort {
    
    PlaylistDomain save(PlaylistDomain playlistDomain);
    
    List<PlaylistDomain> findAll();
    
    Optional<PlaylistDomain> findByName(String name);
    
    boolean existsByName(String name);
    
    void deleteByName(String name);
} 