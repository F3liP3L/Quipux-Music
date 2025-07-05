package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.mapper.UserMapper;
import com.quipuxmusic.core.domain.domains.User;
import com.quipuxmusic.core.domain.usecase.AuthenticateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AuthenticateUserFacade implements AuthFacadePort {
    
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final UserMapper userMapper;
    
    @Autowired
    public AuthenticateUserFacade(AuthenticateUserUseCase authenticateUserUseCase,
                                 UserMapper userMapper) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.userMapper = userMapper;
    }
    @Override
    public LoginResponseDTO execute(LoginRequestDTO loginRequestDTO) {
        User user = userMapper.toDomain(loginRequestDTO);
        return authenticateUserUseCase.execute(user);
    }
} 