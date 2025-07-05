package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.mapper.UserMapper;
import com.quipuxmusic.core.domain.domains.UserDomain;
import com.quipuxmusic.core.domain.exception.AuthenticationException;
import com.quipuxmusic.core.domain.port.JwtServicePort;
import com.quipuxmusic.core.domain.port.PasswordEncoderPort;
import com.quipuxmusic.core.domain.port.UserRepositoryPort;
import com.quipuxmusic.core.domain.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - AuthenticateUserUseCaseImpl")
class AuthenticateUserDomainUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Mock
    private JwtServicePort jwtServicePort;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserValidator userValidator;

    private AuthenticateUserUseCaseImpl authenticateUserUseCase;

    @BeforeEach
    void setUp() {
        authenticateUserUseCase = new AuthenticateUserUseCaseImpl(
            userRepositoryPort, passwordEncoderPort, jwtServicePort, userMapper, userValidator
        );
    }

    @Test
    @DisplayName("Debería autenticar usuario exitosamente")
    void shouldAuthenticateUserSuccessfully() {
        UserDomain loginUserDomain = new UserDomain();
        loginUserDomain.setUsername("testuser");
        loginUserDomain.setPassword("password123");

        UserDomain storedUserDomain = new UserDomain();
        storedUserDomain.setId(1L);
        storedUserDomain.setUsername("testuser");
        storedUserDomain.setPassword("encodedPassword123");
        storedUserDomain.setRole("USER");

        LoginResponseDTO expectedResponse = new LoginResponseDTO();
        expectedResponse.setToken("jwtToken123");
        expectedResponse.setMensaje("Login exitoso");

        when(userRepositoryPort.findByUsername("testuser")).thenReturn(Optional.of(storedUserDomain));
        when(passwordEncoderPort.matches("password123", "encodedPassword123")).thenReturn(true);
        when(jwtServicePort.generateToken(storedUserDomain)).thenReturn("jwtToken123");
        when(userMapper.toLoginResponseDTO(storedUserDomain, "jwtToken123")).thenReturn(expectedResponse);

        LoginResponseDTO result = authenticateUserUseCase.execute(loginUserDomain);

        assertNotNull(result);
        assertEquals("jwtToken123", result.getToken());
        assertEquals("Login exitoso", result.getMensaje());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort).findByUsername("testuser");
        verify(passwordEncoderPort).matches("password123", "encodedPassword123");
        verify(jwtServicePort).generateToken(storedUserDomain);
        verify(userMapper).toLoginResponseDTO(storedUserDomain, "jwtToken123");
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username es null")
    void shouldThrowExceptionWhenUsernameIsNull() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername(null);
        userDomain.setPassword("password123");

        doThrow(new IllegalArgumentException("El nombre de usuario es requerido"))
            .when(userValidator).validateLoginRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(userDomain)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username está vacío")
    void shouldThrowExceptionWhenUsernameIsEmpty() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("");
        userDomain.setPassword("password123");

        doThrow(new IllegalArgumentException("El nombre de usuario es requerido"))
            .when(userValidator).validateLoginRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(userDomain)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username tiene solo espacios")
    void shouldThrowExceptionWhenUsernameHasOnlySpaces() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("   ");
        userDomain.setPassword("password123");

        doThrow(new IllegalArgumentException("El nombre de usuario es requerido"))
            .when(userValidator).validateLoginRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(userDomain)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password es null")
    void shouldThrowExceptionWhenPasswordIsNull() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword(null);

        doThrow(new IllegalArgumentException("La contraseña es requerida"))
            .when(userValidator).validateLoginRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(userDomain)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password está vacío")
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword("");

        doThrow(new IllegalArgumentException("La contraseña es requerida"))
            .when(userValidator).validateLoginRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(userDomain)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password tiene solo espacios")
    void shouldThrowExceptionWhenPasswordHasOnlySpaces() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("testuser");
        userDomain.setPassword("   ");

        doThrow(new IllegalArgumentException("La contraseña es requerida"))
            .when(userValidator).validateLoginRequest(any());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(userDomain)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar AuthenticationException cuando usuario no existe")
    void shouldThrowAuthenticationExceptionWhenUserDoesNotExist() {
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername("nonexistentuser");
        userDomain.setPassword("password123");

        when(userRepositoryPort.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        AuthenticationException exception = assertThrows(
            AuthenticationException.class,
            () -> authenticateUserUseCase.execute(userDomain)
        );
        assertEquals("Usuario o contraseña inválidos", exception.getMessage());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort).findByUsername("nonexistentuser");
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar AuthenticationException cuando password no coincide")
    void shouldThrowAuthenticationExceptionWhenPasswordDoesNotMatch() {
        UserDomain loginUserDomain = new UserDomain();
        loginUserDomain.setUsername("testuser");
        loginUserDomain.setPassword("wrongpassword");

        UserDomain storedUserDomain = new UserDomain();
        storedUserDomain.setId(1L);
        storedUserDomain.setUsername("testuser");
        storedUserDomain.setPassword("encodedPassword123");
        storedUserDomain.setRole("USER");

        when(userRepositoryPort.findByUsername("testuser")).thenReturn(Optional.of(storedUserDomain));
        when(passwordEncoderPort.matches("wrongpassword", "encodedPassword123")).thenReturn(false);

        AuthenticationException exception = assertThrows(
            AuthenticationException.class,
            () -> authenticateUserUseCase.execute(loginUserDomain)
        );
        assertEquals("Usuario o contraseña inválidos", exception.getMessage());

        verify(userValidator).validateLoginRequest(any());
        verify(userRepositoryPort).findByUsername("testuser");
        verify(passwordEncoderPort).matches("wrongpassword", "encodedPassword123");
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería generar token JWT cuando autenticación es exitosa")
    void shouldGenerateJwtTokenWhenAuthenticationIsSuccessful() {
        UserDomain loginUserDomain = new UserDomain();
        loginUserDomain.setUsername("testuser");
        loginUserDomain.setPassword("password123");

        UserDomain storedUserDomain = new UserDomain();
        storedUserDomain.setId(1L);
        storedUserDomain.setUsername("testuser");
        storedUserDomain.setPassword("encodedPassword123");
        storedUserDomain.setRole("USER");

        LoginResponseDTO expectedResponse = new LoginResponseDTO();
        expectedResponse.setToken("generatedJwtToken");
        expectedResponse.setMensaje("Login exitoso");

        when(userRepositoryPort.findByUsername("testuser")).thenReturn(Optional.of(storedUserDomain));
        when(passwordEncoderPort.matches("password123", "encodedPassword123")).thenReturn(true);
        when(jwtServicePort.generateToken(storedUserDomain)).thenReturn("generatedJwtToken");
        when(userMapper.toLoginResponseDTO(storedUserDomain, "generatedJwtToken")).thenReturn(expectedResponse);

        LoginResponseDTO result = authenticateUserUseCase.execute(loginUserDomain);

        assertEquals("generatedJwtToken", result.getToken());
        verify(jwtServicePort).generateToken(storedUserDomain);
        verify(userMapper).toLoginResponseDTO(storedUserDomain, "generatedJwtToken");
    }
} 