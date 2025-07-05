package com.quipuxmusic.core.domain.validator;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.domain.exception.DuplicatePlaylistException;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaylistValidator {
    
    private final PlaylistRepositoryPort playlistRepositoryPort;
    
    @Autowired
    public PlaylistValidator(PlaylistRepositoryPort playlistRepositoryPort) {
        this.playlistRepositoryPort = playlistRepositoryPort;
    }
    
    public void validateCreatePlaylist(PlaylistDTO playlistDTO) {
        validateName(playlistDTO.getNombre());
        validateNotDuplicate(playlistDTO.getNombre());
    }
    
    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la lista de reproducción no puede ser nulo o vacío");
        }
    }
    
    public void validateNotDuplicate(String name) {
        if (playlistRepositoryPort.existsByName(name)) {
            throw new DuplicatePlaylistException("Ya existe una lista de reproducción con el nombre '" + name + "'");
        }
    }
    
    public void validateExists(String name) {
        if (!playlistRepositoryPort.existsByName(name)) {
            throw new IllegalArgumentException("No se encontró la lista de reproducción con el nombre '" + name + "'");
        }
    }
} 