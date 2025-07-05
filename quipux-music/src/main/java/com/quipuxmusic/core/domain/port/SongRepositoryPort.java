package com.quipuxmusic.core.domain.port;

import com.quipuxmusic.core.domain.domains.SongDomain;

public interface SongRepositoryPort {
    
    SongDomain save(SongDomain songDomain);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
} 