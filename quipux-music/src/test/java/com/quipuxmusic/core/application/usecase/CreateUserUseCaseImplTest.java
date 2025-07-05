package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.domain.domains.User;
import com.quipuxmusic.core.domain.port.PasswordEncoderPort;
import com.quipuxmusic.core.domain.port.UserRepositoryPort;
import com.quipuxmusic.core.domain.usecase.CreateUserUseCase;
import com.quipuxmusic.core.domain.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - CreateUserUseCaseImpl")
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Mock
    private UserValidator userValidator;

    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp() {
        createUserUseCase = new CreateUseUseCaseImpl(userRepositoryPort, passwordEncoderPort, userValidator);
    }

    @Test
    @DisplayName("Debería crear usuario exitosamente")
    void shouldCreateUserSuccessfully() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepositoryPort.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoderPort.encode("password123")).thenReturn("encodedPassword123");
        MessageResponseDTO result = createUserUseCase.execute(user);

        assertNotNull(result);
        assertEquals("Usuario registrado exitosamente", result.getMensaje());
        assertEquals("EXITO", result.getTipo());
        assertEquals("encodedPassword123", user.getPassword());

        verify(userRepositoryPort).existsByUsername("testuser");
        verify(passwordEncoderPort).encode("password123");
        verify(userRepositoryPort).save(user);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username es null")
    void shouldThrowExceptionWhenUsernameIsNull() {
        User user = new User();
        user.setUsername(null);
        user.setPassword("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(user)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userRepositoryPort, never()).existsByUsername(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username está vacío")
    void shouldThrowExceptionWhenUsernameIsEmpty() {
        User user = new User();
        user.setUsername("");
        user.setPassword("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(user)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userRepositoryPort, never()).existsByUsername(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username tiene solo espacios")
    void shouldThrowExceptionWhenUsernameHasOnlySpaces() {
        User user = new User();
        user.setUsername("   ");
        user.setPassword("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(user)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userRepositoryPort, never()).existsByUsername(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password es null")
    void shouldThrowExceptionWhenPasswordIsNull() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(user)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userRepositoryPort, never()).existsByUsername(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password está vacío")
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("");


        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(user)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userRepositoryPort, never()).existsByUsername(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password tiene solo espacios")
    void shouldThrowExceptionWhenPasswordHasOnlySpaces() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("   ");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(user)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userRepositoryPort, never()).existsByUsername(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username ya existe")
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword("password123");

        when(userRepositoryPort.existsByUsername("existinguser")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(user)
        );
        assertEquals("El nombre de usuario ya existe", exception.getMessage());

        verify(userRepositoryPort).existsByUsername("existinguser");
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería encriptar password antes de guardar")
    void shouldEncodePasswordBeforeSaving() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("plainPassword");

        when(userRepositoryPort.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoderPort.encode("plainPassword")).thenReturn("encodedPassword");
        createUserUseCase.execute(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(passwordEncoderPort).encode("plainPassword");
        verify(userRepositoryPort).save(user);
    }

    @Test
    @DisplayName("Debería mantener username original después de encriptar password")
    void shouldKeepOriginalUsernameAfterEncodingPassword() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepositoryPort.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoderPort.encode("password123")).thenReturn("encodedPassword123");

        createUserUseCase.execute(user);

        assertEquals("testuser", user.getUsername());
        assertEquals("encodedPassword123", user.getPassword());
    }
} 