package com.quipuxmusic.core.domain.usecase;

import com.quipuxmusic.core.application.dto.PlaylistDTO;

public interface GetPlaylistByNameUseCase {
    
    PlaylistDTO execute(String name);
} 