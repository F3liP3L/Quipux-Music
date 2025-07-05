package com.quipuxmusic.core.domain.usecase;

import com.quipuxmusic.core.domain.domains.UserDomain;
import com.quipuxmusic.core.application.dto.MessageResponseDTO;

public interface CreateUserUseCase {
    
    MessageResponseDTO execute(UserDomain userDomain);
} 