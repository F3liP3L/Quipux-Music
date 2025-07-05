package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.mapper.PlaylistMapper;
import com.quipuxmusic.core.domain.domains.Playlist;
import com.quipuxmusic.core.domain.usecase.CreatePlaylistUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CreatePlaylistFacade implements CreatePlaylistFacadePort {
    private final CreatePlaylistUseCase createPlaylistUseCase;
    private final PlaylistMapper playlistMapper;
    @Autowired
    public CreatePlaylistFacade(CreatePlaylistUseCase createPlaylistUseCase,
                               PlaylistMapper playlistMapper) {
        this.createPlaylistUseCase = createPlaylistUseCase;
        this.playlistMapper = playlistMapper;
    }
    @Override
    public PlaylistDTO execute(PlaylistDTO playlistDTO) {
        Playlist playlist = playlistMapper.toDomain(playlistDTO);
        return createPlaylistUseCase.execute(playlist);
    }
} 