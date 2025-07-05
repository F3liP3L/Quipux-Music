package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;

public interface AuthFacadePort {
    LoginResponseDTO execute(LoginRequestDTO loginRequestDTO);
} 