package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.dto.SongDTO;
import com.quipuxmusic.core.domain.domains.Playlist;
import com.quipuxmusic.core.domain.domains.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - PlaylistMapper")
class PlaylistMapperTest {

    @Mock
    private SongMapper songMapper;

    private PlaylistMapper playlistMapper;

    @BeforeEach
    void setUp() {
        playlistMapper = new PlaylistMapper(songMapper);
    }

    @Test
    @DisplayName("Debería mapear Playlist a PlaylistDTO exitosamente")
    void shouldMapPlaylistToPlaylistDTOSuccessfully() {
        // Arrange
        Song song1 = new Song();
        song1.setId(1L);
        song1.setTitle("Canción 1");
        song1.setArtist("Artista 1");
        song1.setAlbum("Álbum 1");
        song1.setYear(String.valueOf(2020));

        Song song2 = new Song();
        song2.setId(2L);
        song2.setTitle("Canción 2");
        song2.setArtist("Artista 2");
        song2.setAlbum("Álbum 2");
        song2.setYear(String.valueOf(2021));

        List<Song> songs = Arrays.asList(song1, song2);

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("Mi Playlist");
        playlist.setDescription("Descripción de prueba");
        playlist.setSongs(songs);

        SongDTO songDTO1 = new SongDTO();
        songDTO1.setTitulo("Canción 1");
        songDTO1.setArtista("Artista 1");
        songDTO1.setAlbum("Álbum 1");
        songDTO1.setAnno("2020");

        SongDTO songDTO2 = new SongDTO();
        songDTO2.setTitulo("Canción 2");
        songDTO2.setArtista("Artista 2");
        songDTO2.setAlbum("Álbum 2");
        songDTO2.setAnno("2021");

        List<SongDTO> songDTOs = Arrays.asList(songDTO1, songDTO2);

        when(songMapper.toDTO(song1)).thenReturn(songDTO1);
        when(songMapper.toDTO(song2)).thenReturn(songDTO2);

        // Act
        PlaylistDTO result = playlistMapper.toDTO(playlist);

        // Assert
        assertNotNull(result);
        assertEquals("Mi Playlist", result.getNombre());
        assertEquals("Descripción de prueba", result.getDescripcion());
        assertEquals(2, result.getCanciones().size());
        assertEquals("Canción 1", result.getCanciones().get(0).getTitulo());
        assertEquals("Canción 2", result.getCanciones().get(1).getTitulo());

        verify(songMapper).toDTO(song1);
        verify(songMapper).toDTO(song2);
    }

    @Test
    @DisplayName("Debería mapear Playlist sin canciones a PlaylistDTO")
    void shouldMapPlaylistWithoutSongsToPlaylistDTO() {
        // Arrange
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("Playlist Vacía");
        playlist.setDescription("Sin canciones");
        playlist.setSongs(Collections.emptyList());

        // Act
        PlaylistDTO result = playlistMapper.toDTO(playlist);

        // Assert
        assertNotNull(result);
        assertEquals("Playlist Vacía", result.getNombre());
        assertEquals("Sin canciones", result.getDescripcion());
        assertTrue(result.getCanciones().isEmpty());

        verify(songMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Debería mapear PlaylistDTO a Playlist exitosamente")
    void shouldMapPlaylistDTOToPlaylistSuccessfully() {
        // Arrange
        SongDTO songDTO1 = new SongDTO();
        songDTO1.setTitulo("Canción 1");
        songDTO1.setArtista("Artista 1");
        songDTO1.setAlbum("Álbum 1");
        songDTO1.setAnno("2020");

        SongDTO songDTO2 = new SongDTO();
        songDTO2.setTitulo("Canción 2");
        songDTO2.setArtista("Artista 2");
        songDTO2.setAlbum("Álbum 2");
        songDTO2.setAnno("2021");

        List<SongDTO> songDTOs = Arrays.asList(songDTO1, songDTO2);

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Mi Playlist");
        playlistDTO.setDescripcion("Descripción de prueba");
        playlistDTO.setCanciones(songDTOs);

        Song song1 = new Song();
        song1.setId(1L);
        song1.setTitle("Canción 1");
        song1.setArtist("Artista 1");
        song1.setAlbum("Álbum 1");
        song1.setYear("2020");

        Song song2 = new Song();
        song2.setId(2L);
        song2.setTitle("Canción 2");
        song2.setArtist("Artista 2");
        song2.setAlbum("Álbum 2");
        song2.setYear("2021");

        when(songMapper.toDomain(songDTO1)).thenReturn(song1);
        when(songMapper.toDomain(songDTO2)).thenReturn(song2);

        // Act
        Playlist result = playlistMapper.toDomain(playlistDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Mi Playlist", result.getName());
        assertEquals("Descripción de prueba", result.getDescription());
        assertEquals(2, result.getSongs().size());
        assertEquals("Canción 1", result.getSongs().get(0).getTitle());
        assertEquals("Canción 2", result.getSongs().get(1).getTitle());

        verify(songMapper).toDomain(songDTO1);
        verify(songMapper).toDomain(songDTO2);
    }

    @Test
    @DisplayName("Debería mapear PlaylistDTO sin canciones a Playlist")
    void shouldMapPlaylistDTOWithoutSongsToPlaylist() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Playlist Vacía");
        playlistDTO.setDescripcion("Sin canciones");
        playlistDTO.setCanciones(Collections.emptyList());

        // Act
        Playlist result = playlistMapper.toDomain(playlistDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Playlist Vacía", result.getName());
        assertEquals("Sin canciones", result.getDescription());
        assertTrue(result.getSongs().isEmpty());

        verify(songMapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Debería mapear PlaylistDTO con valores null")
    void shouldMapPlaylistDTOWithNullValues() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre(null);
        playlistDTO.setDescripcion(null);
        playlistDTO.setCanciones(null);

        // Act
        Playlist result = playlistMapper.toDomain(playlistDTO);

        // Assert
        assertNotNull(result);
        assertNull(result.getName());
        assertNull(result.getDescription());
        assertNull(result.getSongs());

        verify(songMapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Debería mapear Playlist con valores null")
    void shouldMapPlaylistWithNullValues() {
        // Arrange
        Playlist playlist = new Playlist();
        playlist.setId(null);
        playlist.setName(null);
        playlist.setDescription(null);
        playlist.setSongs(null);

        // Act
        PlaylistDTO result = playlistMapper.toDTO(playlist);

        // Assert
        assertNotNull(result);
        assertNull(result.getNombre());
        assertNull(result.getDescripcion());
        assertNull(result.getCanciones());

        verify(songMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Debería mapear PlaylistDTO con canciones null")
    void shouldMapPlaylistDTOWithNullSongs() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Playlist con canciones null");
        playlistDTO.setDescripcion("Descripción");
        playlistDTO.setCanciones(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> playlistMapper.toDomain(playlistDTO));
    }

    @Test
    @DisplayName("Debería mapear Playlist con canciones null")
    void shouldMapPlaylistWithNullSongs() {
        // Arrange
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("Playlist con canciones null");
        playlist.setDescription("Descripción");
        playlist.setSongs(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> playlistMapper.toDTO(playlist));
    }
} 