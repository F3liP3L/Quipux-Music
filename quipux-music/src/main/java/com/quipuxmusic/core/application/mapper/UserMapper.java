package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.domain.domains.UserDomain;
import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public LoginResponseDTO toLoginResponseDTO(UserDomain userDomain, String token) {
        return new LoginResponseDTO(token, userDomain.getUsername(), userDomain.getRole());
    }
    
    public UserDomain toDomain(LoginRequestDTO loginRequestDTO) {
        return new UserDomain(loginRequestDTO.getNombreUsuario(), loginRequestDTO.getContrasena());
    }
    
    public UserDomain toDomain(RegisterRequestDTO registerRequestDTO) {
        return new UserDomain(
            registerRequestDTO.getNombreUsuario(),
            registerRequestDTO.getContrasena(),
            registerRequestDTO.getRol()
        );
    }
} 