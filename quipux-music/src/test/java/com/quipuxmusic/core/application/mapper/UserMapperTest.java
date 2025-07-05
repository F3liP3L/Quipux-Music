package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.application.dto.LoginRequestDTO;
import com.quipuxmusic.core.application.dto.LoginResponseDTO;
import com.quipuxmusic.core.application.dto.RegisterRequestDTO;
import com.quipuxmusic.core.domain.domains.UserDomain;
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
        UserDomain userDomain = new UserDomain();
        userDomain.setId(1L);
        userDomain.setUsername("testuser");
        userDomain.setPassword("password123");
        userDomain.setRole("USER");

        String token = "jwtToken123";

        LoginResponseDTO result = userMapper.toLoginResponseDTO(userDomain, token);

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

        UserDomain result = userMapper.toDomain(loginRequestDTO);

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

        UserDomain result = userMapper.toDomain(registerRequestDTO);

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

        UserDomain result = userMapper.toDomain(registerRequestDTO);

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

        UserDomain result = userMapper.toDomain(registerRequestDTO);

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

        UserDomain result = userMapper.toDomain(loginRequestDTO);

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

        UserDomain result = userMapper.toDomain(registerRequestDTO);

        assertNotNull(result);
        assertNull(result.getUsername());
        assertNull(result.getPassword());
        assertNull(result.getRole());
        assertNull(result.getId());
    }

    @Test
    @DisplayName("Debería mapear User a LoginResponseDTO con token null")
    void shouldMapUserToLoginResponseDTOWithNullToken() {
        UserDomain userDomain = new UserDomain();
        userDomain.setId(1L);
        userDomain.setUsername("testuser");
        userDomain.setPassword("password123");
        userDomain.setRole("USER");

        String token = null;

        LoginResponseDTO result = userMapper.toLoginResponseDTO(userDomain, token);

        assertNotNull(result);
        assertNull(result.getToken());
        assertEquals("testuser", result.getNombreUsuario());
        assertEquals("USER", result.getRol());
    }

    @Test
    @DisplayName("Debería mapear User a LoginResponseDTO con token vacío")
    void shouldMapUserToLoginResponseDTOWithEmptyToken() {
        UserDomain userDomain = new UserDomain();
        userDomain.setId(1L);
        userDomain.setUsername("testuser");
        userDomain.setPassword("password123");
        userDomain.setRole("USER");

        String token = "";

        LoginResponseDTO result = userMapper.toLoginResponseDTO(userDomain, token);

        assertNotNull(result);
        assertEquals("", result.getToken());
        assertEquals("testuser", result.getNombreUsuario());
        assertEquals("USER", result.getRol());
    }

    @Test
    @DisplayName("Debería mapear User a LoginResponseDTO con valores null en User")
    void shouldMapUserToLoginResponseDTOWithNullValuesInUser() {
        UserDomain userDomain = new UserDomain();
        userDomain.setId(null);
        userDomain.setUsername(null);
        userDomain.setPassword(null);
        userDomain.setRole(null);

        String token = "testToken";

        LoginResponseDTO result = userMapper.toLoginResponseDTO(userDomain, token);

        assertNotNull(result);
        assertEquals("testToken", result.getToken());
        assertNull(result.getNombreUsuario());
        assertNull(result.getRol());
    }
} 