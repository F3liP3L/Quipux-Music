package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.mapper.UserMapper;
import com.quipuxmusic.core.domain.domains.User;
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
class AuthenticateUserUseCaseImplTest {

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
        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("password123");

        User storedUser = new User();
        storedUser.setId(1L);
        storedUser.setUsername("testuser");
        storedUser.setPassword("encodedPassword123");
        storedUser.setRole("USER");

        LoginResponseDTO expectedResponse = new LoginResponseDTO();
        expectedResponse.setToken("jwtToken123");
        expectedResponse.setMensaje("Login exitoso");

        when(userRepositoryPort.findByUsername("testuser")).thenReturn(Optional.of(storedUser));
        when(passwordEncoderPort.matches("password123", "encodedPassword123")).thenReturn(true);
        when(jwtServicePort.generateToken(storedUser)).thenReturn("jwtToken123");
        when(userMapper.toLoginResponseDTO(storedUser, "jwtToken123")).thenReturn(expectedResponse);

        LoginResponseDTO result = authenticateUserUseCase.execute(loginUser);

        assertNotNull(result);
        assertEquals("jwtToken123", result.getToken());
        assertEquals("Login exitoso", result.getMensaje());

        verify(userRepositoryPort).findByUsername("testuser");
        verify(passwordEncoderPort).matches("password123", "encodedPassword123");
        verify(jwtServicePort).generateToken(storedUser);
        verify(userMapper).toLoginResponseDTO(storedUser, "jwtToken123");
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username es null")
    void shouldThrowExceptionWhenUsernameIsNull() {
        User user = new User();
        user.setUsername(null);
        user.setPassword("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(user)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username está vacío")
    void shouldThrowExceptionWhenUsernameIsEmpty() {
        User user = new User();
        user.setUsername("");
        user.setPassword("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(user)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando username tiene solo espacios")
    void shouldThrowExceptionWhenUsernameHasOnlySpaces() {
        User user = new User();
        user.setUsername("   ");
        user.setPassword("password123");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(user)
        );
        assertEquals("El nombre de usuario es requerido", exception.getMessage());

        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password es null")
    void shouldThrowExceptionWhenPasswordIsNull() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(user)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password está vacío")
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(user)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando password tiene solo espacios")
    void shouldThrowExceptionWhenPasswordHasOnlySpaces() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("   ");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> authenticateUserUseCase.execute(user)
        );
        assertEquals("La contraseña es requerida", exception.getMessage());

        verify(userRepositoryPort, never()).findByUsername(any());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar AuthenticationException cuando usuario no existe")
    void shouldThrowAuthenticationExceptionWhenUserDoesNotExist() {
        User user = new User();
        user.setUsername("nonexistentuser");
        user.setPassword("password123");

        when(userRepositoryPort.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        AuthenticationException exception = assertThrows(
            AuthenticationException.class,
            () -> authenticateUserUseCase.execute(user)
        );
        assertEquals("Usuario o contraseña inválidos", exception.getMessage());

        verify(userRepositoryPort).findByUsername("nonexistentuser");
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería lanzar AuthenticationException cuando password no coincide")
    void shouldThrowAuthenticationExceptionWhenPasswordDoesNotMatch() {
        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("wrongpassword");

        User storedUser = new User();
        storedUser.setId(1L);
        storedUser.setUsername("testuser");
        storedUser.setPassword("encodedPassword123");
        storedUser.setRole("USER");

        when(userRepositoryPort.findByUsername("testuser")).thenReturn(Optional.of(storedUser));
        when(passwordEncoderPort.matches("wrongpassword", "encodedPassword123")).thenReturn(false);

        AuthenticationException exception = assertThrows(
            AuthenticationException.class,
            () -> authenticateUserUseCase.execute(loginUser)
        );
        assertEquals("Usuario o contraseña inválidos", exception.getMessage());

        verify(userRepositoryPort).findByUsername("testuser");
        verify(passwordEncoderPort).matches("wrongpassword", "encodedPassword123");
        verify(jwtServicePort, never()).generateToken(any());
        verify(userMapper, never()).toLoginResponseDTO(any(), any());
    }

    @Test
    @DisplayName("Debería generar token JWT cuando autenticación es exitosa")
    void shouldGenerateJwtTokenWhenAuthenticationIsSuccessful() {
        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("password123");

        User storedUser = new User();
        storedUser.setId(1L);
        storedUser.setUsername("testuser");
        storedUser.setPassword("encodedPassword123");
        storedUser.setRole("USER");

        LoginResponseDTO expectedResponse = new LoginResponseDTO();
        expectedResponse.setToken("generatedJwtToken");
        expectedResponse.setMensaje("Login exitoso");

        when(userRepositoryPort.findByUsername("testuser")).thenReturn(Optional.of(storedUser));
        when(passwordEncoderPort.matches("password123", "encodedPassword123")).thenReturn(true);
        when(jwtServicePort.generateToken(storedUser)).thenReturn("generatedJwtToken");
        when(userMapper.toLoginResponseDTO(storedUser, "generatedJwtToken")).thenReturn(expectedResponse);

        LoginResponseDTO result = authenticateUserUseCase.execute(loginUser);

        assertEquals("generatedJwtToken", result.getToken());
        verify(jwtServicePort).generateToken(storedUser);
        verify(userMapper).toLoginResponseDTO(storedUser, "generatedJwtToken");
    }
} 