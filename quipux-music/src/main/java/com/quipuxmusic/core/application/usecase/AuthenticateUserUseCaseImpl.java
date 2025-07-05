package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.mapper.UserMapper;
import com.quipuxmusic.core.domain.domains.User;
import com.quipuxmusic.core.domain.exception.AuthenticationException;
import com.quipuxmusic.core.domain.port.JwtServicePort;
import com.quipuxmusic.core.domain.port.PasswordEncoderPort;
import com.quipuxmusic.core.domain.port.UserRepositoryPort;
import com.quipuxmusic.core.domain.usecase.AuthenticateUserUseCase;
import com.quipuxmusic.core.domain.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {
    
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtServicePort jwtServicePort;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    
    @Autowired
    public AuthenticateUserUseCaseImpl(UserRepositoryPort userRepositoryPort,
                                     PasswordEncoderPort passwordEncoderPort,
                                     JwtServicePort jwtServicePort,
                                     UserMapper userMapper,
                                     UserValidator userValidator) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtServicePort = jwtServicePort;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }
    
    @Override
    public LoginResponseDTO execute(User user) {
        // Validar datos del usuario
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        
        // Buscar usuario en el repositorio
        Optional<User> userOpt = userRepositoryPort.findByUsername(user.getUsername());
        
        if (userOpt.isPresent() && passwordEncoderPort.matches(user.getPassword(), userOpt.get().getPassword())) {
            String token = jwtServicePort.generateToken(userOpt.get());
            return userMapper.toLoginResponseDTO(userOpt.get(), token);
        }
        
        throw new AuthenticationException("Usuario o contraseña inválidos");
    }
} 