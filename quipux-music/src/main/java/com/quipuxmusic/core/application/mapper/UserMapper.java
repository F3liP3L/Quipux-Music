package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.domain.domains.User;
import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public LoginResponseDTO toLoginResponseDTO(User user, String token) {
        return new LoginResponseDTO(token, user.getUsername(), user.getRole());
    }
    
    public User toDomain(LoginRequestDTO loginRequestDTO) {
        return new User(loginRequestDTO.getNombreUsuario(), loginRequestDTO.getContrasena());
    }
    
    public User toDomain(RegisterRequestDTO registerRequestDTO) {
        return new User(
            registerRequestDTO.getNombreUsuario(),
            registerRequestDTO.getContrasena(),
            registerRequestDTO.getRol()
        );
    }
} 