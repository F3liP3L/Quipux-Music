package com.quipuxmusic.core.application.mapper;

import com.quipuxmusic.core.domain.domains.Playlist;
import com.quipuxmusic.core.domain.domains.Song;
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
    
    public PlaylistDTO toDTO(Playlist playlist) {
        List<SongDTO> songDTOs = playlist.getSongs().stream()
                .map(songMapper::toDTO)
                .collect(Collectors.toList());
        
        return new PlaylistDTO(playlist.getName(), playlist.getDescription(), songDTOs);
    }
    
    public Playlist toDomain(PlaylistDTO playlistDTO) {
        List<Song> songs = playlistDTO.getCanciones().stream()
                .map(songMapper::toDomain)
                .collect(Collectors.toList());
        
        return new Playlist(playlistDTO.getNombre(), playlistDTO.getDescripcion(), songs);
    }
} 