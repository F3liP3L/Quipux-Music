package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.domain.domains.PlaylistDomain;
import com.quipuxmusic.core.domain.domains.SongDomain;
import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.dto.SongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaylistMapper {
    
    private final SongMapper songMapper;
    
    @Autowired
    public PlaylistMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }
    
    public PlaylistDTO toDTO(PlaylistDomain playlistDomain) {
        List<SongDTO> songDTOs = playlistDomain.getSongs().stream()
                .map(songMapper::toDTO)
                .collect(Collectors.toList());
        
        return new PlaylistDTO(playlistDomain.getName(), playlistDomain.getDescription(), songDTOs);
    }
    
    public PlaylistDomain toDomain(PlaylistDTO playlistDTO) {
        List<SongDomain> songDomains = playlistDTO.getCanciones().stream()
                .map(songMapper::toDomain)
                .collect(Collectors.toList());
        
        return new PlaylistDomain(playlistDTO.getNombre(), playlistDTO.getDescripcion(), songDomains);
    }
} 