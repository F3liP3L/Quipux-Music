package com.quipuxmusic.core.domain.usecase;

import com.quipuxmusic.core.domain.domains.UserDomain;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;

public interface AuthenticateUserUseCase {
    
    LoginResponseDTO execute(UserDomain userDomain);
} 