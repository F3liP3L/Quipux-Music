package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;

public interface CreateUserFacadePort {
    MessageResponseDTO execute(RegisterRequestDTO registerRequestDTO);
}