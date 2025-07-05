package com.quipuxmusic.core.domain.port;

import com.quipuxmusic.core.domain.domains.Playlist;
import java.util.List;
import java.util.Optional;

public interface PlaylistRepositoryPort {
    
    Playlist save(Playlist playlist);
    
    List<Playlist> findAll();
    
    Optional<Playlist> findByName(String name);
    
    boolean existsByName(String name);
    
    void deleteByName(String name);
} 