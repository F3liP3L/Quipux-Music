package com.quipuxmusic.core.domain.usecase;

import com.quipuxmusic.core.domain.domains.User;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;

public interface AuthenticateUserUseCase {
    
    LoginResponseDTO execute(User user);
} 