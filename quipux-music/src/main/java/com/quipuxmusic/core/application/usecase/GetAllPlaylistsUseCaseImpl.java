package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.mapper.PlaylistMapper;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import com.quipuxmusic.core.domain.usecase.GetAllPlaylistsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllPlaylistsUseCaseImpl implements GetAllPlaylistsUseCase {
    
    private final PlaylistRepositoryPort playlistRepositoryPort;
    private final PlaylistMapper playlistMapper;
    
    @Autowired
    public GetAllPlaylistsUseCaseImpl(PlaylistRepositoryPort playlistRepositoryPort,
                                    PlaylistMapper playlistMapper) {
        this.playlistRepositoryPort = playlistRepositoryPort;
        this.playlistMapper = playlistMapper;
    }
    
    @Override
    public List<PlaylistDTO> execute() {
        return playlistRepositoryPort.findAll().stream()
                .map(playlistMapper::toDTO)
                .collect(Collectors.toList());
    }
} 