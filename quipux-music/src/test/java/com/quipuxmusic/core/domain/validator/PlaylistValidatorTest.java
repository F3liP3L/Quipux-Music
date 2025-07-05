package com.quipuxmusic.core.domain.validator;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.dto.SongDTO;
import com.quipuxmusic.core.domain.exception.DuplicatePlaylistException;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

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

    // Pruebas para validación de fechas de canciones
    @Test
    @DisplayName("Debería validar playlist con canciones con fechas válidas")
    void shouldValidatePlaylistWithSongsWithValidDates() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        
        SongDTO song1 = new SongDTO();
        song1.setTitulo("Canción 1");
        song1.setAnno("2020");
        
        SongDTO song2 = new SongDTO();
        song2.setTitulo("Canción 2");
        song2.setAnno("2023");
        
        playlistDTO.setCanciones(Arrays.asList(song1, song2));

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateCreatePlaylist(playlistDTO));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }

    @Test
    @DisplayName("Debería validar playlist con canciones sin fecha")
    void shouldValidatePlaylistWithSongsWithoutDate() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        
        SongDTO song1 = new SongDTO();
        song1.setTitulo("Canción 1");
        song1.setAnno(null);
        
        SongDTO song2 = new SongDTO();
        song2.setTitulo("Canción 2");
        song2.setAnno("");
        
        playlistDTO.setCanciones(Arrays.asList(song1, song2));

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateCreatePlaylist(playlistDTO));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }

    @Test
    @DisplayName("Debería validar playlist sin canciones")
    void shouldValidatePlaylistWithoutSongs() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        playlistDTO.setCanciones(Collections.emptyList());

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateCreatePlaylist(playlistDTO));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }

    @Test
    @DisplayName("Debería validar playlist con canciones null")
    void shouldValidatePlaylistWithNullSongs() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        playlistDTO.setCanciones(null);

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateCreatePlaylist(playlistDTO));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando canción tiene fecha futura")
    void shouldThrowExceptionWhenSongHasFutureDate() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        
        SongDTO song = new SongDTO();
        song.setTitulo("Canción Futura");
        song.setAnno("2030");
        
        playlistDTO.setCanciones(Arrays.asList(song));

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateCreatePlaylist(playlistDTO)
        );
        assertTrue(exception.getMessage().contains("La fecha de la canción 'Canción Futura' (2030) no puede ser mayor al año actual"));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando canción tiene año inválido")
    void shouldThrowExceptionWhenSongHasInvalidYear() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        
        SongDTO song = new SongDTO();
        song.setTitulo("Canción Inválida");
        song.setAnno("abc");
        
        playlistDTO.setCanciones(Arrays.asList(song));

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playlistValidator.validateCreatePlaylist(playlistDTO)
        );
        assertTrue(exception.getMessage().contains("El año de la canción 'Canción Inválida' debe ser un número válido"));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }

    @Test
    @DisplayName("Debería validar fecha exacta del año actual")
    void shouldValidateExactCurrentYear() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        
        int currentYear = java.time.LocalDate.now().getYear();
        SongDTO song = new SongDTO();
        song.setTitulo("Canción Actual");
        song.setAnno(String.valueOf(currentYear));
        
        playlistDTO.setCanciones(Arrays.asList(song));

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateCreatePlaylist(playlistDTO));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }

    @Test
    @DisplayName("Debería validar múltiples canciones con diferentes fechas")
    void shouldValidateMultipleSongsWithDifferentDates() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        
        SongDTO song1 = new SongDTO();
        song1.setTitulo("Canción Antigua");
        song1.setAnno("1990");
        
        SongDTO song2 = new SongDTO();
        song2.setTitulo("Canción Reciente");
        song2.setAnno("2022");
        
        SongDTO song3 = new SongDTO();
        song3.setTitulo("Canción Sin Fecha");
        song3.setAnno(null);
        
        playlistDTO.setCanciones(Arrays.asList(song1, song2, song3));

        when(playlistRepositoryPort.existsByName("Mi Playlist")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> playlistValidator.validateCreatePlaylist(playlistDTO));
        verify(playlistRepositoryPort).existsByName("Mi Playlist");
    }
} 