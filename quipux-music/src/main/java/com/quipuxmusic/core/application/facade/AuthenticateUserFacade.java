package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.mapper.UserMapper;
import com.quipuxmusic.core.domain.domains.UserDomain;
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
        UserDomain userDomain = userMapper.toDomain(loginRequestDTO);
        return authenticateUserUseCase.execute(userDomain);
    }
} 