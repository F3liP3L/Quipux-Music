package com.quipuxmusic.infrastructure.adapter.primary.controller;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.application.facade.AuthFacadePort;
import com.quipuxmusic.core.application.facade.CreateUserFacadePort;
import com.quipuxmusic.core.domain.exception.AuthenticationException;
import com.quipuxmusic.core.domain.exception.DuplicateUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - AuthController")
class AuthControllerTest {

    @Mock
    private AuthFacadePort authenticateUserFacade;
    @Mock
    private CreateUserFacadePort createUserFacade;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authenticateUserFacade, createUserFacade);
    }

    @Test
    @DisplayName("Debería hacer login exitosamente")
    void shouldLoginSuccessfully() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setNombreUsuario("testuser");
        request.setContrasena("password123");

        LoginResponseDTO expectedResponse = new LoginResponseDTO();
        expectedResponse.setToken("jwtToken123");
        expectedResponse.setMensaje("Login exitoso");

        when(authenticateUserFacade.execute(request)).thenReturn(expectedResponse);

        ResponseEntity<?> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        LoginResponseDTO responseBody = (LoginResponseDTO) response.getBody();
        assertEquals("jwtToken123", responseBody.getToken());
        assertEquals("Login exitoso", responseBody.getMensaje());

        verify(authenticateUserFacade).execute(request);
    }

    @Test
    @DisplayName("Debería manejar error de autenticación")
    void shouldHandleAuthenticationError() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setNombreUsuario("invaliduser");
        request.setContrasena("wrongpassword");

        when(authenticateUserFacade.execute(request))
            .thenThrow(new AuthenticationException("Usuario o contraseña inválidos"));

        ResponseEntity<?> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error de autenticación: Usuario o contraseña inválidos", responseBody.getMensaje());

        verify(authenticateUserFacade).execute(request);
    }

    @Test
    @DisplayName("Debería manejar excepción genérica en login")
    void shouldHandleGenericExceptionInLogin() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO();
        request.setNombreUsuario("testuser");
        request.setContrasena("password123");

        when(authenticateUserFacade.execute(request))
            .thenThrow(new RuntimeException("Error interno del servidor"));

        ResponseEntity<?> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error de autenticación: Error interno del servidor", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());

        verify(authenticateUserFacade).execute(request);
    }

    @Test
    @DisplayName("Debería registrar usuario exitosamente")
    void shouldRegisterUserSuccessfully() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario("newuser");
        request.setContrasena("password123");
        request.setRol("USER");

        MessageResponseDTO expectedResponse = new MessageResponseDTO();
        expectedResponse.setMensaje("Usuario registrado exitosamente");
        expectedResponse.setTipo("EXITO");

        when(createUserFacade.execute(request)).thenReturn(expectedResponse);

        ResponseEntity<?> response = authController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Usuario registrado exitosamente", responseBody.getMensaje());
        assertEquals("EXITO", responseBody.getTipo());

        verify(createUserFacade).execute(request);
    }

    @Test
    @DisplayName("Debería manejar error de usuario duplicado en registro")
    void shouldHandleDuplicateUserErrorInRegister() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario("existinguser");
        request.setContrasena("password123");
        request.setRol("USER");

        when(createUserFacade.execute(request))
            .thenThrow(new DuplicateUserException("El nombre de usuario ya existe"));

        ResponseEntity<?> response = authController.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error de registro: El nombre de usuario ya existe", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());

        verify(createUserFacade).execute(request);
    }

    @Test
    @DisplayName("Debería manejar excepción genérica en registro")
    void shouldHandleGenericExceptionInRegister() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario("newuser");
        request.setContrasena("password123");
        request.setRol("USER");

        when(createUserFacade.execute(request))
            .thenThrow(new RuntimeException("Error interno del servidor"));

        ResponseEntity<?> response = authController.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error de registro: Error interno del servidor", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());

        verify(createUserFacade).execute(request);
    }

    @Test
    @DisplayName("Debería crear usuarios de prueba exitosamente")
    void shouldCreateTestUsersSuccessfully() {
        RegisterRequestDTO adminRequest = new RegisterRequestDTO("admin", "admin123", "ADMIN");
        RegisterRequestDTO userRequest = new RegisterRequestDTO("usuario", "password123", "USER");
        RegisterRequestDTO testRequest = new RegisterRequestDTO("test", "test123", "USER");

        MessageResponseDTO adminResponse = new MessageResponseDTO("Usuario registrado exitosamente", "EXITO");
        MessageResponseDTO userResponse = new MessageResponseDTO("Usuario registrado exitosamente", "EXITO");
        MessageResponseDTO testResponse = new MessageResponseDTO("Usuario registrado exitosamente", "EXITO");

        when(createUserFacade.execute(any(RegisterRequestDTO.class))).thenReturn(adminResponse);

        ResponseEntity<?> response = authController.createTestUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertTrue(responseBody.getMensaje().contains("Usuarios de prueba creados exitosamente"));
        assertTrue(responseBody.getMensaje().contains("admin / admin123 (ADMIN)"));
        assertTrue(responseBody.getMensaje().contains("usuario / password123 (USER)"));
        assertTrue(responseBody.getMensaje().contains("test / test123 (USER)"));
        assertEquals("EXITO", responseBody.getTipo());

        verify(createUserFacade, times(3)).execute(any(RegisterRequestDTO.class));
    }
} 