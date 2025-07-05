package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.domain.domains.UserDomain;
import com.quipuxmusic.core.domain.port.PasswordEncoderPort;
import com.quipuxmusic.core.domain.port.UserRepositoryPort;
import com.quipuxmusic.core.domain.usecase.CreateUserUseCase;
import com.quipuxmusic.core.domain.validator.UserValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CreateUseUseCaseImpl implements CreateUserUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final UserValidator userValidator;

    public CreateUseUseCaseImpl(UserRepositoryPort userRepositoryPort, PasswordEncoderPort passwordEncoderPort, UserValidator userValidator) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.userValidator = userValidator;
    }

    @Override
    public MessageResponseDTO execute(UserDomain userDomain) {
        var registerRequest = new RegisterRequestDTO();
        registerRequest.setNombreUsuario(userDomain.getUsername());
        registerRequest.setContrasena(userDomain.getPassword());
        userValidator.validateRegisterRequest(registerRequest);

        var encodedPassword = passwordEncoderPort.encode(userDomain.getPassword());
        userDomain.setPassword(encodedPassword);

        userRepositoryPort.save(userDomain);
        return new MessageResponseDTO("Usuario registrado exitosamente", "EXITO");
    }
}