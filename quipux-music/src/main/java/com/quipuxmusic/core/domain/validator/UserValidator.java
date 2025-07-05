package com.quipuxmusic.core.domain.validator;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.domain.exception.DuplicateUserException;
import com.quipuxmusic.core.domain.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    
    private final UserRepositoryPort userRepositoryPort;
    
    @Autowired
    public UserValidator(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }
    
    public void validateLoginRequest(LoginRequestDTO request) {
        if (request.getNombreUsuario() == null || request.getNombreUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        if (request.getContrasena() == null || request.getContrasena().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
    }
    
    public void validateRegistroRequest(RegisterRequestDTO request) {
        if (request.getNombreUsuario() == null || request.getNombreUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        if (request.getContrasena() == null || request.getContrasena().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        validateNotDuplicate(request.getNombreUsuario());
    }
    
    public void validateNotDuplicate(String username) {
        if (userRepositoryPort.existsByUsername(username)) {
            throw new DuplicateUserException("El nombre de usuario ya existe");
        }
    }
} 