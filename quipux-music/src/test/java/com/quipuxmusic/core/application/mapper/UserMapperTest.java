package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.domain.domains.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - UserMapper")
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("Debería mapear User a LoginResponseDTO exitosamente")
    void shouldMapUserToLoginResponseDTOSuccessfully() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("USER");

        String token = "jwtToken123";

        LoginResponseDTO result = userMapper.toLoginResponseDTO(user, token);

        assertNotNull(result);
        assertEquals("jwtToken123", result.getToken());
        assertEquals("testuser", result.getNombreUsuario());
        assertEquals("USER", result.getRol());
    }

    @Test
    @DisplayName("Debería mapear LoginRequestDTO a User exitosamente")
    void shouldMapLoginRequestDTOToUserSuccessfully() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setNombreUsuario("loginuser");
        loginRequestDTO.setContrasena("loginpassword");

        User result = userMapper.toDomain(loginRequestDTO);

        assertNotNull(result);
        assertEquals("loginuser", result.getUsername());
        assertEquals("loginpassword", result.getPassword());
        assertNull(result.getId());
    }

    @Test
    @DisplayName("Debería mapear RegisterRequestDTO a User exitosamente")
    void shouldMapRegisterRequestDTOToUserSuccessfully() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setNombreUsuario("registeruser");
        registerRequestDTO.setContrasena("registerpassword");
        registerRequestDTO.setRol("ADMIN");

        User result = userMapper.toDomain(registerRequestDTO);

        assertNotNull(result);
        assertEquals("registeruser", result.getUsername());
        assertEquals("registerpassword", result.getPassword());
        assertEquals("ADMIN", result.getRole());
        assertNull(result.getId());
    }

    @Test
    @DisplayName("Debería mapear RegisterRequestDTO a User con rol por defecto cuando rol es null")
    void shouldMapRegisterRequestDTOToUserWithDefaultRoleWhenRoleIsNull() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setNombreUsuario("registeruser");
        registerRequestDTO.setContrasena("registerpassword");
        registerRequestDTO.setRol(null);

        User result = userMapper.toDomain(registerRequestDTO);

        assertNotNull(result);
        assertEquals("registeruser", result.getUsername());
        assertEquals("registerpassword", result.getPassword());
        assertNull(result.getRole());
        assertNull(result.getId());
    }

    @Test
    @DisplayName("Debería mapear RegisterRequestDTO a User con rol por defecto cuando rol está vacío")
    void shouldMapRegisterRequestDTOToUserWithDefaultRoleWhenRoleIsEmpty() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setNombreUsuario("registeruser");
        registerRequestDTO.setContrasena("registerpassword");
        registerRequestDTO.setRol("");

        User result = userMapper.toDomain(registerRequestDTO);

        assertNotNull(result);
        assertEquals("registeruser", result.getUsername());
        assertEquals("registerpassword", result.getPassword());
        assertEquals("", result.getRole());
        assertNull(result.getId());
    }

    @Test
    @DisplayName("Debería mapear LoginRequestDTO con valores null")
    void shouldMapLoginRequestDTOWithNullValues() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setNombreUsuario(null);
        loginRequestDTO.setContrasena(null);

        User result = userMapper.toDomain(loginRequestDTO);

        assertNotNull(result);
        assertNull(result.getUsername());
        assertNull(result.getPassword());
        assertNull(result.getId());
        assertNotNull(result.getRole());
    }

    @Test
    @DisplayName("Debería mapear RegisterRequestDTO con valores null")
    void shouldMapRegisterRequestDTOWithNullValues() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setNombreUsuario(null);
        registerRequestDTO.setContrasena(null);
        registerRequestDTO.setRol(null);

        User result = userMapper.toDomain(registerRequestDTO);

        assertNotNull(result);
        assertNull(result.getUsername());
        assertNull(result.getPassword());
        assertNull(result.getRole());
        assertNull(result.getId());
    }

    @Test
    @DisplayName("Debería mapear User a LoginResponseDTO con token null")
    void shouldMapUserToLoginResponseDTOWithNullToken() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("USER");

        String token = null;

        LoginResponseDTO result = userMapper.toLoginResponseDTO(user, token);

        assertNotNull(result);
        assertNull(result.getToken());
        assertEquals("testuser", result.getNombreUsuario());
        assertEquals("USER", result.getRol());
    }

    @Test
    @DisplayName("Debería mapear User a LoginResponseDTO con token vacío")
    void shouldMapUserToLoginResponseDTOWithEmptyToken() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("USER");

        String token = "";

        LoginResponseDTO result = userMapper.toLoginResponseDTO(user, token);

        assertNotNull(result);
        assertEquals("", result.getToken());
        assertEquals("testuser", result.getNombreUsuario());
        assertEquals("USER", result.getRol());
    }

    @Test
    @DisplayName("Debería mapear User a LoginResponseDTO con valores null en User")
    void shouldMapUserToLoginResponseDTOWithNullValuesInUser() {
        User user = new User();
        user.setId(null);
        user.setUsername(null);
        user.setPassword(null);
        user.setRole(null);

        String token = "testToken";

        LoginResponseDTO result = userMapper.toLoginResponseDTO(user, token);

        assertNotNull(result);
        assertEquals("testToken", result.getToken());
        assertNull(result.getNombreUsuario());
        assertNull(result.getRol());
    }
} 