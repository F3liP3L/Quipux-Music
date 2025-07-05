package com.quipuxmusic.core.domain.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - User Domain")
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("Debería crear User con constructor vacío")
    void shouldCreateUserWithEmptyConstructor() {
        // Act
        User user = new User();

        // Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    @DisplayName("Debería crear User con constructor de username y password")
    void shouldCreateUserWithUsernameAndPasswordConstructor() {
        // Act
        User user = new User("testuser", "password123");

        // Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    @DisplayName("Debería crear User con constructor de username, password y role")
    void shouldCreateUserWithUsernamePasswordAndRoleConstructor() {
        // Act
        User user = new User("adminuser", "adminpass", "ADMIN");

        // Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertEquals("adminuser", user.getUsername());
        assertEquals("adminpass", user.getPassword());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    @DisplayName("Debería crear User con constructor completo")
    void shouldCreateUserWithFullConstructor() {
        // Act
        User user = new User(1L, "testuser", "password123", "USER");

        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    @DisplayName("Debería establecer y obtener id correctamente")
    void shouldSetAndGetIdCorrectly() {
        // Arrange
        Long expectedId = 123L;

        // Act
        user.setId(expectedId);

        // Assert
        assertEquals(expectedId, user.getId());
    }

    @Test
    @DisplayName("Debería establecer y obtener username correctamente")
    void shouldSetAndGetUsernameCorrectly() {
        // Arrange
        String expectedUsername = "testuser";

        // Act
        user.setUsername(expectedUsername);

        // Assert
        assertEquals(expectedUsername, user.getUsername());
    }

    @Test
    @DisplayName("Debería establecer y obtener password correctamente")
    void shouldSetAndGetPasswordCorrectly() {
        // Arrange
        String expectedPassword = "securepassword123";

        // Act
        user.setPassword(expectedPassword);

        // Assert
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    @DisplayName("Debería establecer y obtener role correctamente")
    void shouldSetAndGetRoleCorrectly() {
        // Arrange
        String expectedRole = "MODERATOR";

        // Act
        user.setRole(expectedRole);

        // Assert
        assertEquals(expectedRole, user.getRole());
    }

    @Test
    @DisplayName("Debería manejar valores null en setters")
    void shouldHandleNullValuesInSetters() {
        // Act
        user.setId(null);
        user.setUsername(null);
        user.setPassword(null);
        user.setRole(null);

        // Assert
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    @DisplayName("Debería manejar valores vacíos en setters")
    void shouldHandleEmptyValuesInSetters() {
        // Act
        user.setUsername("");
        user.setPassword("");
        user.setRole("");

        // Assert
        assertEquals("", user.getUsername());
        assertEquals("", user.getPassword());
        assertEquals("", user.getRole());
    }

    @Test
    @DisplayName("Debería ser igual a sí mismo")
    void shouldBeEqualToItself() {
        // Arrange
        user.setUsername("testuser");

        // Act & Assert
        assertEquals(user, user);
    }

    @Test
    @DisplayName("Debería ser igual a otro User con mismo username")
    void shouldBeEqualToOtherUserWithSameUsername() {
        // Arrange
        User user1 = new User("testuser", "password1", "USER");
        User user2 = new User("testuser", "password2", "ADMIN");

        // Act & Assert
        assertEquals(user1, user2);
    }

    @Test
    @DisplayName("No debería ser igual a otro User con diferente username")
    void shouldNotBeEqualToOtherUserWithDifferentUsername() {
        // Arrange
        User user1 = new User("user1", "password1", "USER");
        User user2 = new User("user2", "password1", "USER");

        // Act & Assert
        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("No debería ser igual a null")
    void shouldNotBeEqualToNull() {
        // Arrange
        user.setUsername("testuser");

        // Act & Assert
        assertNotEquals(null, user);
    }

    @Test
    @DisplayName("No debería ser igual a objeto de diferente tipo")
    void shouldNotBeEqualToObjectOfDifferentType() {
        // Arrange
        user.setUsername("testuser");
        String differentObject = "testuser";

        // Act & Assert
        assertNotEquals(user, differentObject);
    }

    @Test
    @DisplayName("Debería tener mismo hashCode para Users con mismo username")
    void shouldHaveSameHashCodeForUsersWithSameUsername() {
        // Arrange
        User user1 = new User("testuser", "password1", "USER");
        User user2 = new User("testuser", "password2", "ADMIN");

        // Act & Assert
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    @DisplayName("Debería tener diferente hashCode para Users con diferente username")
    void shouldHaveDifferentHashCodeForUsersWithDifferentUsername() {
        // Arrange
        User user1 = new User("user1", "password1", "USER");
        User user2 = new User("user2", "password1", "USER");

        // Act & Assert
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    @DisplayName("Debería generar toString correcto")
    void shouldGenerateCorrectToString() {
        // Arrange
        user.setId(1L);
        user.setUsername("testuser");
        user.setRole("USER");

        // Act
        String result = user.toString();

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
        String result = user.toString();

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
        user.setUsername(specialUsername);

        // Assert
        assertEquals(specialUsername, user.getUsername());
    }

    @Test
    @DisplayName("Debería manejar role con espacios")
    void shouldHandleRoleWithSpaces() {
        // Arrange
        String roleWithSpaces = "SUPER ADMIN";

        // Act
        user.setRole(roleWithSpaces);

        // Assert
        assertEquals(roleWithSpaces, user.getRole());
    }
} 