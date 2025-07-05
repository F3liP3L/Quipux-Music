package com.quipuxmusic.infrastructure.adapter.primary.controller;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.application.facade.AuthFacadePort;
import com.quipuxmusic.core.application.facade.AuthenticateUserFacade;
import com.quipuxmusic.core.application.facade.CreateUserFacade;
import com.quipuxmusic.core.application.facade.CreateUserFacadePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthFacadePort authenticateUserFacade;
    private final CreateUserFacadePort createUserFacade;

    @Autowired
    public AuthController(AuthenticateUserFacade authenticateUserFacade,
                         CreateUserFacade createUserFacade) {
        this.authenticateUserFacade = authenticateUserFacade;
        this.createUserFacade = createUserFacade;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO response = authenticateUserFacade.execute(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO("Error de autenticación: " + e.getMessage(), "ERROR");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            MessageResponseDTO response = createUserFacade.execute(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO("Error de registro: " + e.getMessage(), "ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/create-test-users")
    public ResponseEntity<?> createTestUsers() {
        try {
            createUserFacade.execute(new RegisterRequestDTO("admin", "admin123", "ADMIN"));
            createUserFacade.execute(new RegisterRequestDTO("usuario", "password123", "USER"));
            createUserFacade.execute(new RegisterRequestDTO("test", "test123", "USER"));
            
            String mensaje = "Usuarios de prueba creados exitosamente:\n" +
                "- admin / admin123 (ADMIN)\n" +
                "- usuario / password123 (USER)\n" +
                "- test / test123 (USER)";
            
            MessageResponseDTO response = new MessageResponseDTO(mensaje, "EXITO");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO("Error al crear usuarios: " + e.getMessage(), "ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
} 