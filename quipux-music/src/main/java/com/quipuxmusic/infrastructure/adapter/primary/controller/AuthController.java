package com.quipuxmusic.infrastructure.adapter.primary.controller;

import com.quipuxmusic.core.application.service.AuthService;
import com.quipuxmusic.core.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String token = authService.authenticate(username, password);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String role = request.getOrDefault("role", "USER");
            User user = authService.createUser(username, password, role);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de registro: " + e.getMessage());
        }
    }

    @PostMapping("/create-test-users")
    public ResponseEntity<?> createTestUsers() {
        try {
            // Crear usuarios de prueba
            authService.createUser("admin", "admin123", "ADMIN");
            authService.createUser("usuario", "password123", "USER");
            authService.createUser("test", "test123", "USER");
            
            return ResponseEntity.ok("Usuarios de prueba creados exitosamente:\n" +
                "- admin / admin123 (ADMIN)\n" +
                "- usuario / password123 (USER)\n" +
                "- test / test123 (USER)");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear usuarios: " + e.getMessage());
        }
    }
} 