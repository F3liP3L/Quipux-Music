package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.application.dto.SongDTO;
import com.quipuxmusic.core.domain.domains.SongDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias - SongMapper")
class SongMapperTest {

    private SongMapper songMapper;

    @BeforeEach
    void setUp() {
        songMapper = new SongMapper();
    }

    @Test
    @DisplayName("Debería mapear Song a SongDTO exitosamente")
    void shouldMapSongToSongDTOSuccessfully() {
        SongDomain songDomain = new SongDomain();
        songDomain.setId(1L);
        songDomain.setTitle("Bohemian Rhapsody");
        songDomain.setArtist("Queen");
        songDomain.setAlbum("A Night at the Opera");
        songDomain.setYear("1975");
        songDomain.setGenre("Rock");

        SongDTO result = songMapper.toDTO(songDomain);

        assertNotNull(result);
        assertEquals("Bohemian Rhapsody", result.getTitulo());
        assertEquals("Queen", result.getArtista());
        assertEquals("A Night at the Opera", result.getAlbum());
        assertEquals("1975", result.getAnno());
        assertEquals("Rock", result.getGenero());
    }

    @Test
    @DisplayName("Debería mapear SongDTO a Song exitosamente")
    void shouldMapSongDTOToSongSuccessfully() {
        SongDTO songDTO = new SongDTO();
        songDTO.setTitulo("Imagine");
        songDTO.setArtista("John Lennon");
        songDTO.setAlbum("Imagine");
        songDTO.setAnno("1971");
        songDTO.setGenero("Pop");

        SongDomain result = songMapper.toDomain(songDTO);

        assertNotNull(result);
        assertEquals("Imagine", result.getTitle());
        assertEquals("John Lennon", result.getArtist());
        assertEquals("Imagine", result.getAlbum());
        assertEquals("1971", result.getYear());
        assertEquals("Pop", result.getGenre());
    }

    @Test
    @DisplayName("Debería mapear Song con valores null")
    void shouldMapSongWithNullValues() {
        SongDomain songDomain = new SongDomain();
        songDomain.setId(null);
        songDomain.setTitle(null);
        songDomain.setArtist(null);
        songDomain.setAlbum(null);
        songDomain.setYear(null);
        songDomain.setGenre(null);

        SongDTO result = songMapper.toDTO(songDomain);

        assertNotNull(result);
        assertNull(result.getTitulo());
        assertNull(result.getArtista());
        assertNull(result.getAlbum());
        assertNull(result.getAnno());
        assertNull(result.getGenero());
    }

    @Test
    @DisplayName("Debería mapear SongDTO con valores null")
    void shouldMapSongDTOWithNullValues() {
        SongDTO songDTO = new SongDTO();
        songDTO.setTitulo(null);
        songDTO.setArtista(null);
        songDTO.setAlbum(null);
        songDTO.setAnno(null);
        songDTO.setGenero(null);

        SongDomain result = songMapper.toDomain(songDTO);

        assertNotNull(result);
        assertNull(result.getTitle());
        assertNull(result.getArtist());
        assertNull(result.getAlbum());
        assertNull(result.getYear());
        assertNull(result.getGenre());
    }

    @Test
    @DisplayName("Debería mapear Song con valores vacíos")
    void shouldMapSongWithEmptyValues() {
        SongDomain songDomain = new SongDomain();
        songDomain.setId(1L);
        songDomain.setTitle("");
        songDomain.setArtist("");
        songDomain.setAlbum("");
        songDomain.setYear("0");
        songDomain.setGenre("");

        SongDTO result = songMapper.toDTO(songDomain);

        assertNotNull(result);
        assertEquals("", result.getTitulo());
        assertEquals("", result.getArtista());
        assertEquals("", result.getAlbum());
        assertEquals("0", result.getAnno());
        assertEquals("", result.getGenero());
    }

    @Test
    @DisplayName("Debería mapear SongDTO con valores vacíos")
    void shouldMapSongDTOWithEmptyValues() {
        SongDTO songDTO = new SongDTO();
        songDTO.setTitulo("");
        songDTO.setArtista("");
        songDTO.setAlbum("");
        songDTO.setAnno("0");
        songDTO.setGenero("");

        SongDomain result = songMapper.toDomain(songDTO);

        assertNotNull(result);
        assertEquals("", result.getTitle());
        assertEquals("", result.getArtist());
        assertEquals("", result.getAlbum());
        assertEquals("0", result.getYear());
        assertEquals("", result.getGenre());
    }

    @Test
    @DisplayName("Debería mapear Song con año negativo")
    void shouldMapSongWithNegativeYear() {
        SongDomain songDomain = new SongDomain();
        songDomain.setId(1L);
        songDomain.setTitle("Canción Antigua");
        songDomain.setArtist("Artista Antiguo");
        songDomain.setAlbum("Álbum Antiguo");
        songDomain.setYear("-100");
        songDomain.setGenre("Clásica");

        SongDTO result = songMapper.toDTO(songDomain);

        assertNotNull(result);
        assertEquals("Canción Antigua", result.getTitulo());
        assertEquals("Artista Antiguo", result.getArtista());
        assertEquals("Álbum Antiguo", result.getAlbum());
        assertEquals("-100", result.getAnno());
        assertEquals("Clásica", result.getGenero());
    }

    @Test
    @DisplayName("Debería mapear SongDTO con año negativo")
    void shouldMapSongDTOWithNegativeYear() {
        SongDTO songDTO = new SongDTO();
        songDTO.setTitulo("Canción Antigua");
        songDTO.setArtista("Artista Antiguo");
        songDTO.setAlbum("Álbum Antiguo");
        songDTO.setAnno("-100");
        songDTO.setGenero("Clásica");

        SongDomain result = songMapper.toDomain(songDTO);

        assertNotNull(result);
        assertEquals("Canción Antigua", result.getTitle());
        assertEquals("Artista Antiguo", result.getArtist());
        assertEquals("Álbum Antiguo", result.getAlbum());
        assertEquals("-100", result.getYear());
        assertEquals("Clásica", result.getGenre());
    }

    @Test
    @DisplayName("Debería mapear Song con caracteres especiales")
    void shouldMapSongWithSpecialCharacters() {
        SongDomain songDomain = new SongDomain();
        songDomain.setId(1L);
        songDomain.setTitle("Canción con ñ y áéíóú");
        songDomain.setArtist("Artista con símbolos @#$%");
        songDomain.setAlbum("Álbum con números 123");
        songDomain.setYear("2023");
        songDomain.setGenre("Género con emoji 🎵");

        SongDTO result = songMapper.toDTO(songDomain);

        assertNotNull(result);
        assertEquals("Canción con ñ y áéíóú", result.getTitulo());
        assertEquals("Artista con símbolos @#$%", result.getArtista());
        assertEquals("Álbum con números 123", result.getAlbum());
        assertEquals("2023", result.getAnno());
        assertEquals("Género con emoji 🎵", result.getGenero());
    }

    @Test
    @DisplayName("Debería mapear SongDTO con caracteres especiales")
    void shouldMapSongDTOWithSpecialCharacters() {
        SongDTO songDTO = new SongDTO();
        songDTO.setTitulo("Canción con ñ y áéíóú");
        songDTO.setArtista("Artista con símbolos @#$%");
        songDTO.setAlbum("Álbum con números 123");
        songDTO.setAnno("2023");
        songDTO.setGenero("Género con emoji 🎵");

        SongDomain result = songMapper.toDomain(songDTO);

        assertNotNull(result);
        assertEquals("Canción con ñ y áéíóú", result.getTitle());
        assertEquals("Artista con símbolos @#$%", result.getArtist());
        assertEquals("Álbum con números 123", result.getAlbum());
        assertEquals("2023", result.getYear());
        assertEquals("Género con emoji 🎵", result.getGenre());
    }
} 