package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.domain.domains.SongDomain;
import com.quipuxmusic.core.application.dto.SongDTO;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {
    
    public SongDTO toDTO(SongDomain songDomain) {
        return new SongDTO(songDomain.getTitle(), songDomain.getArtist(), songDomain.getAlbum(), songDomain.getYear(), songDomain.getGenre());
    }
    
    public SongDomain toDomain(SongDTO songDTO) {
        return new SongDomain(songDTO.getTitulo(), songDTO.getArtista(), songDTO.getAlbum(), songDTO.getAnno(), songDTO.getGenero());
    }
} 