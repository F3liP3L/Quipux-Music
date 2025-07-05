package com.quipuxmusic.core.domain.validator;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.domain.exception.DuplicateUserException;
import com.quipuxmusic.core.domain.port.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - UserValidator")
class UserValidatorTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator(userRepositoryPort);
    }

    @Test
    @DisplayName("Debería validar login request exitosamente")
    void shouldValidateLoginRequestSuccessfully() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setNombreUsuario("testuser");
        request.setContrasena("password123");

        assertDoesNotThrow(() -> userValidator.validateLoginRequest(request));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username es null en login")
    void shouldThrowExceptionWhenUsernameIsNullInLogin() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setNombreUsuario(null);
        request.setContrasena("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userValidator.validateLoginRequest(request)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username está vacío en login")
    void shouldThrowExceptionWhenUsernameIsEmptyInLogin() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setNombreUsuario("");
        request.setContrasena("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userValidator.validateLoginRequest(request)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password es null en login")
    void shouldThrowExceptionWhenPasswordIsNullInLogin() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setNombreUsuario("testuser");
        request.setContrasena(null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userValidator.validateLoginRequest(request)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password está vacío en login")
    void shouldThrowExceptionWhenPasswordIsEmptyInLogin() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setNombreUsuario("testuser");
        request.setContrasena("   ");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userValidator.validateLoginRequest(request)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());
    }

    @Test
    @DisplayName("Debería validar registro request exitosamente")
    void shouldValidateRegisterRequestSuccessfully() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario("newuser");
        request.setContrasena("password123");

        when(userRepositoryPort.existsByUsername("newuser")).thenReturn(false);

        assertDoesNotThrow(() -> userValidator.validateRegistroRequest(request));
        verify(userRepositoryPort).existsByUsername("newuser");
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username es null en registro")
    void shouldThrowExceptionWhenUsernameIsNullInRegister() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario(null);
        request.setContrasena("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userValidator.validateRegistroRequest(request)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username está vacío en registro")
    void shouldThrowExceptionWhenUsernameIsEmptyInRegister() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario("");
        request.setContrasena("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userValidator.validateRegistroRequest(request)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password es null en registro")
    void shouldThrowExceptionWhenPasswordIsNullInRegister() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario("testuser");
        request.setContrasena(null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userValidator.validateRegistroRequest(request)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password está vacío en registro")
    void shouldThrowExceptionWhenPasswordIsEmptyInRegister() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario("testuser");
        request.setContrasena("   ");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userValidator.validateRegistroRequest(request)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar DuplicateUserException cuando username ya existe")
    void shouldThrowDuplicateUserExceptionWhenUsernameExists() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNombreUsuario("existinguser");
        request.setContrasena("password123");
        when(userRepositoryPort.existsByUsername("existinguser")).thenReturn(true);

        DuplicateUserException exception = assertThrows(
            DuplicateUserException.class,
            () -> userValidator.validateRegistroRequest(request)
        );
        assertEquals("El nombre de usuario ya existe", exception.getMessage());
        verify(userRepositoryPort).existsByUsername("existinguser");
    }

    @Test
    @DisplayName("Debería validar que username no es duplicado exitosamente")
    void shouldValidateNotDuplicateSuccessfully() {
        String username = "uniqueuser";
        when(userRepositoryPort.existsByUsername(username)).thenReturn(false);

        assertDoesNotThrow(() -> userValidator.validateNotDuplicate(username));
        verify(userRepositoryPort).existsByUsername(username);
    }

    @Test
    @DisplayName("Debería lanzar DuplicateUserException cuando username es duplicado")
    void shouldThrowDuplicateUserExceptionWhenUsernameIsDuplicate() {
        String username = "duplicateuser";
        when(userRepositoryPort.existsByUsername(username)).thenReturn(true);

        DuplicateUserException exception = assertThrows(
            DuplicateUserException.class,
            () -> userValidator.validateNotDuplicate(username)
        );
        assertEquals("El nombre de usuario ya existe", exception.getMessage());
        verify(userRepositoryPort).existsByUsername(username);
    }
} 