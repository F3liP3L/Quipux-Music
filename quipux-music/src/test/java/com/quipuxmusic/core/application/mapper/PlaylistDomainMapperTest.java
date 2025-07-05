package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.dto.SongDTO;
import com.quipuxmusic.core.domain.domains.PlaylistDomain;
import com.quipuxmusic.core.domain.domains.SongDomain;
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
class PlaylistDomainMapperTest {

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
        SongDomain songDomain1 = new SongDomain();
        songDomain1.setId(1L);
        songDomain1.setTitle("Canción 1");
        songDomain1.setArtist("Artista 1");
        songDomain1.setAlbum("Álbum 1");
        songDomain1.setYear(String.valueOf(2020));

        SongDomain songDomain2 = new SongDomain();
        songDomain2.setId(2L);
        songDomain2.setTitle("Canción 2");
        songDomain2.setArtist("Artista 2");
        songDomain2.setAlbum("Álbum 2");
        songDomain2.setYear(String.valueOf(2021));

        List<SongDomain> songDomains = Arrays.asList(songDomain1, songDomain2);

        PlaylistDomain playlistDomain = new PlaylistDomain();
        playlistDomain.setId(1L);
        playlistDomain.setName("Mi Playlist");
        playlistDomain.setDescription("Descripción de prueba");
        playlistDomain.setSongs(songDomains);

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

        when(songMapper.toDTO(songDomain1)).thenReturn(songDTO1);
        when(songMapper.toDTO(songDomain2)).thenReturn(songDTO2);

        PlaylistDTO result = playlistMapper.toDTO(playlistDomain);

        assertNotNull(result);
        assertEquals("Mi Playlist", result.getNombre());
        assertEquals("Descripción de prueba", result.getDescripcion());
        assertEquals(2, result.getCanciones().size());
        assertEquals("Canción 1", result.getCanciones().get(0).getTitulo());
        assertEquals("Canción 2", result.getCanciones().get(1).getTitulo());

        verify(songMapper).toDTO(songDomain1);
        verify(songMapper).toDTO(songDomain2);
    }

    @Test
    @DisplayName("Debería mapear Playlist sin canciones a PlaylistDTO")
    void shouldMapPlaylistWithoutSongsToPlaylistDTO() {
        PlaylistDomain playlistDomain = new PlaylistDomain();
        playlistDomain.setId(1L);
        playlistDomain.setName("Playlist Vacía");
        playlistDomain.setDescription("Sin canciones");
        playlistDomain.setSongs(Collections.emptyList());

        PlaylistDTO result = playlistMapper.toDTO(playlistDomain);

        assertNotNull(result);
        assertEquals("Playlist Vacía", result.getNombre());
        assertEquals("Sin canciones", result.getDescripcion());
        assertTrue(result.getCanciones().isEmpty());

        verify(songMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Debería mapear PlaylistDTO a Playlist exitosamente")
    void shouldMapPlaylistDTOToPlaylistSuccessfully() {
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

        SongDomain songDomain1 = new SongDomain();
        songDomain1.setId(1L);
        songDomain1.setTitle("Canción 1");
        songDomain1.setArtist("Artista 1");
        songDomain1.setAlbum("Álbum 1");
        songDomain1.setYear("2020");

        SongDomain songDomain2 = new SongDomain();
        songDomain2.setId(2L);
        songDomain2.setTitle("Canción 2");
        songDomain2.setArtist("Artista 2");
        songDomain2.setAlbum("Álbum 2");
        songDomain2.setYear("2021");

        when(songMapper.toDomain(songDTO1)).thenReturn(songDomain1);
        when(songMapper.toDomain(songDTO2)).thenReturn(songDomain2);

        PlaylistDomain result = playlistMapper.toDomain(playlistDTO);

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
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Playlist Vacía");
        playlistDTO.setDescripcion("Sin canciones");
        playlistDTO.setCanciones(Collections.emptyList());

        PlaylistDomain result = playlistMapper.toDomain(playlistDTO);

        assertNotNull(result);
        assertEquals("Playlist Vacía", result.getName());
        assertEquals("Sin canciones", result.getDescription());
        assertTrue(result.getSongs().isEmpty());

        verify(songMapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Debería mapear PlaylistDTO con valores null")
    void shouldMapPlaylistDTOWithNullValues() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre(null);
        playlistDTO.setDescripcion(null);
        playlistDTO.setCanciones(null);

        assertThrows(NullPointerException.class, () -> playlistMapper.toDomain(playlistDTO));
    }

    @Test
    @DisplayName("Debería mapear Playlist con valores null")
    void shouldMapPlaylistWithNullValues() {
        PlaylistDomain playlistDomain = new PlaylistDomain();
        playlistDomain.setId(null);
        playlistDomain.setName(null);
        playlistDomain.setDescription(null);
        playlistDomain.setSongs(null);

        PlaylistDTO result = playlistMapper.toDTO(playlistDomain);

        assertNotNull(result);
        assertNull(result.getNombre());
        assertNull(result.getDescripcion());
        assertTrue(result.getCanciones().isEmpty());

        verify(songMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Debería mapear PlaylistDTO con canciones null")
    void shouldMapPlaylistDTOWithNullSongs() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setNombre("Playlist con canciones null");
        playlistDTO.setDescripcion("Descripción");
        playlistDTO.setCanciones(null);

        assertThrows(NullPointerException.class, () -> playlistMapper.toDomain(playlistDTO));
    }

    @Test
    @DisplayName("Debería mapear Playlist con canciones null")
    void shouldMapPlaylistWithNullSongs() {
        PlaylistDomain playlistDomain = new PlaylistDomain();
        playlistDomain.setId(1L);
        playlistDomain.setName("Playlist con canciones null");
        playlistDomain.setDescription("Descripción");
        playlistDomain.setSongs(null);

        PlaylistDTO result = playlistMapper.toDTO(playlistDomain);

        assertNotNull(result);
        assertEquals("Playlist con canciones null", result.getNombre());
        assertEquals("Descripción", result.getDescripcion());
        assertTrue(result.getCanciones().isEmpty());

        verify(songMapper, never()).toDTO(any());
    }
} 