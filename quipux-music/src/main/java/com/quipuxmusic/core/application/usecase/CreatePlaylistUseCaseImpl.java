package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.mapper.PlaylistMapper;
import com.quipuxmusic.core.domain.domains.Playlist;
import com.quipuxmusic.core.domain.domains.Song;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import com.quipuxmusic.core.domain.port.SongRepositoryPort;
import com.quipuxmusic.core.domain.usecase.CreatePlaylistUseCase;
import com.quipuxmusic.core.domain.validator.PlaylistValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CreatePlaylistUseCaseImpl implements CreatePlaylistUseCase {
    
    private final PlaylistRepositoryPort playlistRepositoryPort;
    private final SongRepositoryPort songRepositoryPort;
    private final PlaylistMapper playlistMapper;
    private final PlaylistValidator playlistValidator;
    
    @Autowired
    public CreatePlaylistUseCaseImpl(PlaylistRepositoryPort playlistRepositoryPort,
                                   SongRepositoryPort songRepositoryPort,
                                   PlaylistMapper playlistMapper,
                                   PlaylistValidator playlistValidator) {
        this.playlistRepositoryPort = playlistRepositoryPort;
        this.songRepositoryPort = songRepositoryPort;
        this.playlistMapper = playlistMapper;
        this.playlistValidator = playlistValidator;
    }
    
    @Override
    public PlaylistDTO execute(Playlist playlist) {
        var response = playlistMapper.toDTO(playlist);
        playlistValidator.validateCreatePlaylist(response);

        if (playlist.getSongs() != null) {
            for (Song song : playlist.getSongs()) {
                song = songRepositoryPort.save(song);
            }
        }
        playlistRepositoryPort.save(playlist);
        return response;
    }
} 