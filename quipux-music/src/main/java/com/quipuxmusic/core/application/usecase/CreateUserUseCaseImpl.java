package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.domain.domains.User;
import com.quipuxmusic.core.domain.port.PasswordEncoderPort;
import com.quipuxmusic.core.domain.port.UserRepositoryPort;
import com.quipuxmusic.core.domain.usecase.CreateUserUseCase;
import com.quipuxmusic.core.domain.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final UserValidator userValidator;
    
    @Autowired
    public CreateUserUseCaseImpl(UserRepositoryPort userRepositoryPort,
                               PasswordEncoderPort passwordEncoderPort,
                               UserValidator userValidator) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.userValidator = userValidator;
    }
    
    @Override
    public MessageResponseDTO execute(User user) {
        // Validar datos del usuario
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }

        // Validar que no exista duplicado
        if (userRepositoryPort.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        
        // Encriptar contraseña
        String encodedPassword = passwordEncoderPort.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        // Guardar usuario
        userRepositoryPort.save(user);
        
        return new MessageResponseDTO("Usuario registrado exitosamente", "EXITO");
    }
} 