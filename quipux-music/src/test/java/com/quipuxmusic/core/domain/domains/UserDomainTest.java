package com.quipuxmusic.core.domain.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - User Domain")
class UserDomainTest {

    private UserDomain userDomain;

    @BeforeEach
    void setUp() {
        userDomain = new UserDomain();
    }

    @Test
    @DisplayName("Debería crear User con constructor vacío")
    void shouldCreateUserWithEmptyConstructor() {
        // Act
        UserDomain userDomain = new UserDomain();

        // Assert
        assertNotNull(userDomain);
        assertNull(userDomain.getId());
        assertNull(userDomain.getUsername());
        assertNull(userDomain.getPassword());
        assertNull(userDomain.getRole());
    }

    @Test
    @DisplayName("Debería crear User con constructor de username y password")
    void shouldCreateUserWithUsernameAndPasswordConstructor() {
        // Act
        UserDomain userDomain = new UserDomain("testuser", "password123");

        // Assert
        assertNotNull(userDomain);
        assertNull(userDomain.getId());
        assertEquals("testuser", userDomain.getUsername());
        assertEquals("password123", userDomain.getPassword());
        assertEquals("USER", userDomain.getRole());
    }

    @Test
    @DisplayName("Debería crear User con constructor de username, password y role")
    void shouldCreateUserWithUsernamePasswordAndRoleConstructor() {
        // Act
        UserDomain userDomain = new UserDomain("adminuser", "adminpass", "ADMIN");

        // Assert
        assertNotNull(userDomain);
        assertNull(userDomain.getId());
        assertEquals("adminuser", userDomain.getUsername());
        assertEquals("adminpass", userDomain.getPassword());
        assertEquals("ADMIN", userDomain.getRole());
    }

    @Test
    @DisplayName("Debería crear User con constructor completo")
    void shouldCreateUserWithFullConstructor() {
        // Act
        UserDomain userDomain = new UserDomain(1L, "testuser", "password123", "USER");

        // Assert
        assertNotNull(userDomain);
        assertEquals(1L, userDomain.getId());
        assertEquals("testuser", userDomain.getUsername());
        assertEquals("password123", userDomain.getPassword());
        assertEquals("USER", userDomain.getRole());
    }

    @Test
    @DisplayName("Debería establecer y obtener id correctamente")
    void shouldSetAndGetIdCorrectly() {
        // Arrange
        Long expectedId = 123L;

        // Act
        userDomain.setId(expectedId);

        // Assert
        assertEquals(expectedId, userDomain.getId());
    }

    @Test
    @DisplayName("Debería establecer y obtener username correctamente")
    void shouldSetAndGetUsernameCorrectly() {
        // Arrange
        String expectedUsername = "testuser";

        // Act
        userDomain.setUsername(expectedUsername);

        // Assert
        assertEquals(expectedUsername, userDomain.getUsername());
    }

    @Test
    @DisplayName("Debería establecer y obtener password correctamente")
    void shouldSetAndGetPasswordCorrectly() {
        // Arrange
        String expectedPassword = "securepassword123";

        // Act
        userDomain.setPassword(expectedPassword);

        // Assert
        assertEquals(expectedPassword, userDomain.getPassword());
    }

    @Test
    @DisplayName("Debería establecer y obtener role correctamente")
    void shouldSetAndGetRoleCorrectly() {
        // Arrange
        String expectedRole = "MODERATOR";

        // Act
        userDomain.setRole(expectedRole);

        // Assert
        assertEquals(expectedRole, userDomain.getRole());
    }

    @Test
    @DisplayName("Debería manejar valores null en setters")
    void shouldHandleNullValuesInSetters() {
        // Act
        userDomain.setId(null);
        userDomain.setUsername(null);
        userDomain.setPassword(null);
        userDomain.setRole(null);

        // Assert
        assertNull(userDomain.getId());
        assertNull(userDomain.getUsername());
        assertNull(userDomain.getPassword());
        assertNull(userDomain.getRole());
    }

    @Test
    @DisplayName("Debería manejar valores vacíos en setters")
    void shouldHandleEmptyValuesInSetters() {
        // Act
        userDomain.setUsername("");
        userDomain.setPassword("");
        userDomain.setRole("");

        // Assert
        assertEquals("", userDomain.getUsername());
        assertEquals("", userDomain.getPassword());
        assertEquals("", userDomain.getRole());
    }

    @Test
    @DisplayName("Debería ser igual a sí mismo")
    void shouldBeEqualToItself() {
        // Arrange
        userDomain.setUsername("testuser");

        // Act & Assert
        assertEquals(userDomain, userDomain);
    }

    @Test
    @DisplayName("Debería ser igual a otro User con mismo username")
    void shouldBeEqualToOtherUserWithSameUsername() {
        // Arrange
        UserDomain userDomain1 = new UserDomain("testuser", "password1", "USER");
        UserDomain userDomain2 = new UserDomain("testuser", "password2", "ADMIN");

        // Act & Assert
        assertEquals(userDomain1, userDomain2);
    }

    @Test
    @DisplayName("No debería ser igual a otro User con diferente username")
    void shouldNotBeEqualToOtherUserWithDifferentUsername() {
        // Arrange
        UserDomain userDomain1 = new UserDomain("user1", "password1", "USER");
        UserDomain userDomain2 = new UserDomain("user2", "password1", "USER");

        // Act & Assert
        assertNotEquals(userDomain1, userDomain2);
    }

    @Test
    @DisplayName("No debería ser igual a null")
    void shouldNotBeEqualToNull() {
        // Arrange
        userDomain.setUsername("testuser");

        // Act & Assert
        assertNotEquals(null, userDomain);
    }

    @Test
    @DisplayName("No debería ser igual a objeto de diferente tipo")
    void shouldNotBeEqualToObjectOfDifferentType() {
        // Arrange
        userDomain.setUsername("testuser");
        String differentObject = "testuser";

        // Act & Assert
        assertNotEquals(userDomain, differentObject);
    }

    @Test
    @DisplayName("Debería tener mismo hashCode para Users con mismo username")
    void shouldHaveSameHashCodeForUsersWithSameUsername() {
        // Arrange
        UserDomain userDomain1 = new UserDomain("testuser", "password1", "USER");
        UserDomain userDomain2 = new UserDomain("testuser", "password2", "ADMIN");

        // Act & Assert
        assertEquals(userDomain1.hashCode(), userDomain2.hashCode());
    }

    @Test
    @DisplayName("Debería tener diferente hashCode para Users con diferente username")
    void shouldHaveDifferentHashCodeForUsersWithDifferentUsername() {
        // Arrange
        UserDomain userDomain1 = new UserDomain("user1", "password1", "USER");
        UserDomain userDomain2 = new UserDomain("user2", "password1", "USER");

        // Act & Assert
        assertNotEquals(userDomain1.hashCode(), userDomain2.hashCode());
    }

    @Test
    @DisplayName("Debería generar toString correcto")
    void shouldGenerateCorrectToString() {
        // Arrange
        userDomain.setId(1L);
        userDomain.setUsername("testuser");
        userDomain.setRole("USER");

        // Act
        String result = userDomain.toString();

        // Assert
        assertTrue(result.contains("User{"));
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("username='testuser'"));
        assertTrue(result.contains("role='USER'"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    @DisplayName("Debería generar toString con valores null")
    void shouldGenerateToStringWithNullValues() {
        // Act
        String result = userDomain.toString();

        // Assert
        assertTrue(result.contains("User{"));
        assertTrue(result.contains("id=null"));
        assertTrue(result.contains("username='null'"));
        assertTrue(result.contains("role='null'"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    @DisplayName("Debería manejar username con caracteres especiales")
    void shouldHandleUsernameWithSpecialCharacters() {
        // Arrange
        String specialUsername = "user@domain.com";

        // Act
        userDomain.setUsername(specialUsername);

        // Assert
        assertEquals(specialUsername, userDomain.getUsername());
    }

    @Test
    @DisplayName("Debería manejar role con espacios")
    void shouldHandleRoleWithSpaces() {
        // Arrange
        String roleWithSpaces = "SUPER ADMIN";

        // Act
        userDomain.setRole(roleWithSpaces);

        // Assert
        assertEquals(roleWithSpaces, userDomain.getRole());
    }
} 