package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.domain.domains.Song;
import com.quipuxmusic.core.application.dto.SongDTO;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {
    
    public SongDTO toDTO(Song song) {
        return new SongDTO(song.getTitle(), song.getArtist(), song.getAlbum(), song.getYear(), song.getGenre());
    }
    
    public Song toDomain(SongDTO songDTO) {
        return new Song(songDTO.getTitulo(), songDTO.getArtista(), songDTO.getAlbum(), songDTO.getAnno(), songDTO.getGenero());
    }
} 