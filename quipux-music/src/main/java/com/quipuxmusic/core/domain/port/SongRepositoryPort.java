package com.quipuxmusic.core.domain.port;

import com.quipuxmusic.core.domain.domains.Song;

public interface SongRepositoryPort {
    
    Song save(Song song);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
} 