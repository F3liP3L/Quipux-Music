package com.quipuxmusic.core.domain.validator;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.domain.exception.DuplicatePlaylistException;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - PlaylistValidator")
class PlaylistValidatorTest {

    @Mock
    private PlaylistRepositoryPort playlistRepositoryPort;

    private PlaylistValidator playlistValidator;

    @BeforeEach
    void setUp() {
        playlistValidator = new PlaylistValidator(playlistRepositoryPort);
    }

    @Test
    @DisplayName("Debería validar creación de playlist exitosamente")
    void shouldValidateCreatePlaylistSuccessfully() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        assertDoesNotThrow(() -> playlistValidator.validateCreatePlaylist(playlistDTO));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando nombre es null en creación")
    void shouldThrowExceptionWhenNameIsNullInCreate() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre(null);
        playlistDTO.setDescripcion("Descripción de prueba");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateCreatePlaylist(playlistDTO)
        );
        assertEquals("El nombre de la lista de reproducción no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando nombre está vacío en creación")
    void shouldThrowExceptionWhenNameIsEmptyInCreate() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("");
        playlistDTO.setDescripcion("Descripción de prueba");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateCreatePlaylist(playlistDTO)
        );
        assertEquals("El nombre de la lista de reproducción no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando nombre tiene solo espacios en creación")
    void shouldThrowExceptionWhenNameHasOnlySpacesInCreate() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("   ");
        playlistDTO.setDescripcion("Descripción de prueba");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateCreatePlaylist(playlistDTO)
        );
        assertEquals("El nombre de la lista de reproducción no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar DuplicatePlaylistException cuando nombre ya existe en creación")
    void shouldThrowDuplicatePlaylistExceptionWhenNameExistsInCreate() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Playlist Existente");
        playlistDTO.setDescripcion("Descripción de prueba");

        when(playlistRepositoryPort.existsByName("Playlist Existente")).thenReturn(true);

        // Act & Assert
        DuplicatePlaylistException exception = assertThrows(
            DuplicatePlaylistException.class,
            () -> playlistValidator.validateCreatePlaylist(playlistDTO)
        );
        assertEquals("Ya existe una lista de reproducción con el nombre 'Playlist Existente'", exception.getMessage());
        verify(playlistRepositoryPort).existsByName("Playlist Existente");
    }

    @Test
    @DisplayName("Debería validar nombre exitosamente")
    void shouldValidateNameSuccessfully() {
        // Arrange
        String name = "Playlist Válida";

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateName(name));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando nombre es null")
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        String name = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateName(name)
        );
        assertEquals("El nombre de la lista de reproducción no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando nombre está vacío")
    void shouldThrowExceptionWhenNameIsEmpty() {
        // Arrange
        String name = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateName(name)
        );
        assertEquals("El nombre de la lista de reproducción no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando nombre tiene solo espacios")
    void shouldThrowExceptionWhenNameHasOnlySpaces() {
        // Arrange
        String name = "   ";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateName(name)
        );
        assertEquals("El nombre de la lista de reproducción no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    @DisplayName("Debería validar que nombre no es duplicado exitosamente")
    void shouldValidateNotDuplicateSuccessfully() {
        // Arrange
        String name = "Playlist Única";
        when(playlistRepositoryPort.existsByName(name)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateNotDuplicate(name));
        verify(playlistRepositoryPort).existsByName(name);
    }

    @Test
    @DisplayName("Debería lanzar DuplicatePlaylistException cuando nombre es duplicado")
    void shouldThrowDuplicatePlaylistExceptionWhenNameIsDuplicate() {
        // Arrange
        String name = "Playlist Duplicada";
        when(playlistRepositoryPort.existsByName(name)).thenReturn(true);

        // Act & Assert
        DuplicatePlaylistException exception = assertThrows(
            DuplicatePlaylistException.class,
            () -> playlistValidator.validateNotDuplicate(name)
        );
        assertEquals("Ya existe una lista de reproducción con el nombre 'Playlist Duplicada'", exception.getMessage());
        verify(playlistRepositoryPort).existsByName(name);
    }

    @Test
    @DisplayName("Debería validar existencia exitosamente")
    void shouldValidateExistsSuccessfully() {
        // Arrange
        String name = "Playlist Existente";
        when(playlistRepositoryPort.existsByName(name)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateExists(name));
        verify(playlistRepositoryPort).existsByName(name);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando playlist no existe")
    void shouldThrowExceptionWhenPlaylistDoesNotExist() {
        // Arrange
        String name = "Playlist Inexistente";
        when(playlistRepositoryPort.existsByName(name)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateExists(name)
        );
        assertEquals("No se encontró la lista de reproducción con el nombre 'Playlist Inexistente'", exception.getMessage());
        verify(playlistRepositoryPort).existsByName(name);
    }
} 