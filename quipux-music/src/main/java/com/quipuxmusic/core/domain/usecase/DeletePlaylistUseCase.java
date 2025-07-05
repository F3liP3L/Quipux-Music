package com.quipuxmusic.core.domain.usecase;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;

public interface DeletePlaylistUseCase {
    
    MessageResponseDTO execute(String name);
} 