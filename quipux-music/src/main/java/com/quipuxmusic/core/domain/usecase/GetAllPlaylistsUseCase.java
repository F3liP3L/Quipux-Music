package com.quipuxmusic.core.domain.usecase;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import java.util.List;

public interface GetAllPlaylistsUseCase {
    
    List<PlaylistDTO> execute();
} 