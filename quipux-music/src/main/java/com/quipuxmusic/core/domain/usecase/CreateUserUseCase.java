package com.quipuxmusic.core.domain.usecase;

import com.quipuxmusic.core.domain.domains.User;
import com.quipuxmusic.core.application.dto.MessageResponseDTO;

public interface CreateUserUseCase {
    
    MessageResponseDTO execute(User user);
} 