package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.mapper.PlaylistMapper;
import com.quipuxmusic.core.domain.domains.PlaylistDomain;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import com.quipuxmusic.core.domain.usecase.GetPlaylistByNameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetPlaylistByNameUseCaseImpl implements GetPlaylistByNameUseCase {
    
    private final PlaylistRepositoryPort playlistRepositoryPort;
    private final PlaylistMapper playlistMapper;
    
    @Autowired
    public GetPlaylistByNameUseCaseImpl(PlaylistRepositoryPort playlistRepositoryPort,
                                      PlaylistMapper playlistMapper) {
        this.playlistRepositoryPort = playlistRepositoryPort;
        this.playlistMapper = playlistMapper;
    }
    
    @Override
    public PlaylistDTO execute(String name) {
        Optional<PlaylistDomain> playlist = playlistRepositoryPort.findByName(name);
        if (playlist.isPresent()) {
            return playlistMapper.toDTO(playlist.get());
        } else {
            throw new IllegalArgumentException("No se encontró la lista de reproducción con el nombre '" + name + "'");
        }
    }
} 