package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.domain.domains.UserDomain;
import com.quipuxmusic.core.domain.exception.DuplicateUserException;
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
class CreateUserDomainUseCaseImplTest {

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
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword("password123");

        when(passwordEncoderPort.encode("password123")).thenReturn("encodedPassword123");
        MessageResponseDTO result = createUserUseCase.execute(userDomain);

        assertNotNull(result);
        assertEquals("Usuario registrado exitosamente", result.getMensaje());
        assertEquals("EXITO", result.getTipo());
        assertEquals("encodedPassword123", userDomain.getPassword());

        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort).encode("password123");
        verify(userRepositoryPort).save(userDomain);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username es null")
    void shouldThrowExceptionWhenUsernameIsNull() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername(null);
        userDomain.setPassword("password123");

        doThrow(new IllegalArgumentException("El nombre de usuario es requerido"))
            .when(userValidator).validateRegisterRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(userDomain)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username está vacío")
    void shouldThrowExceptionWhenUsernameIsEmpty() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("");
        userDomain.setPassword("password123");

        doThrow(new IllegalArgumentException("El nombre de usuario es requerido"))
            .when(userValidator).validateRegisterRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(userDomain)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username tiene solo espacios")
    void shouldThrowExceptionWhenUsernameHasOnlySpaces() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("   ");
        userDomain.setPassword("password123");

        doThrow(new IllegalArgumentException("El nombre de usuario es requerido"))
            .when(userValidator).validateRegisterRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(userDomain)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password es null")
    void shouldThrowExceptionWhenPasswordIsNull() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword(null);

        doThrow(new IllegalArgumentException("La contraseña es requerida"))
            .when(userValidator).validateRegisterRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(userDomain)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password está vacío")
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword("");

        doThrow(new IllegalArgumentException("La contraseña es requerida"))
            .when(userValidator).validateRegisterRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(userDomain)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password tiene solo espacios")
    void shouldThrowExceptionWhenPasswordHasOnlySpaces() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword("   ");

        doThrow(new IllegalArgumentException("La contraseña es requerida"))
            .when(userValidator).validateRegisterRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createUserUseCase.execute(userDomain)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username ya existe")
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("existinguser");
        userDomain.setPassword("password123");

        doThrow(new DuplicateUserException("El nombre de usuario ya existe"))
            .when(userValidator).validateRegisterRequest(any());

        DuplicateUserException exception = assertThrows(
            DuplicateUserException.class,
            () -> createUserUseCase.execute(userDomain)
        );
        assertEquals("El nombre de usuario ya existe", exception.getMessage());

        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort, never()).encode(any());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Debería encriptar password antes de guardar")
    void shouldEncodePasswordBeforeSaving() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword("plainPassword");

        when(passwordEncoderPort.encode("plainPassword")).thenReturn("encodedPassword");
        createUserUseCase.execute(userDomain);

        assertEquals("encodedPassword", userDomain.getPassword());
        verify(userValidator).validateRegisterRequest(any());
        verify(passwordEncoderPort).encode("plainPassword");
        verify(userRepositoryPort).save(userDomain);
    }

    @Test
    @DisplayName("Debería mantener username original después de encriptar password")
    void shouldKeepOriginalUsernameAfterEncodingPassword() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword("password123");

        when(passwordEncoderPort.encode("password123")).thenReturn("encodedPassword123");

        createUserUseCase.execute(userDomain);

        assertEquals("testuser", userDomain.getUsername());
        assertEquals("encodedPassword123", userDomain.getPassword());
        verify(userValidator).validateRegisterRequest(any());
    }
} 