package com.quipuxmusic.core.domain.usecase;

import com.quipuxmusic.core.domain.domains.Playlist;
import com.quipuxmusic.core.application.dto.PlaylistDTO;

public interface CreatePlaylistUseCase {
    
    PlaylistDTO execute(Playlist playlist);
} 