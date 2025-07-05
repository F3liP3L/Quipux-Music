package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.application.mapper.UserMapper;
import com.quipuxmusic.core.domain.domains.User;
import com.quipuxmusic.core.domain.usecase.CreateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CreateUserFacade implements CreateUserFacadePort {
    
    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;
    
    @Autowired
    public CreateUserFacade(CreateUserUseCase createUserUseCase,
                           UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.userMapper = userMapper;
    }
    @Override
    public MessageResponseDTO execute(RegisterRequestDTO registerRequestDTO) {
        User user = userMapper.toDomain(registerRequestDTO);
        return createUserUseCase.execute(user);
    }
} 