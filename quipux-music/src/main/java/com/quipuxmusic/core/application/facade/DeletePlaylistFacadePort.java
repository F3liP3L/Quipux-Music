package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;

public interface DeletePlaylistFacadePort {
    MessageResponseDTO execute(String name);
}