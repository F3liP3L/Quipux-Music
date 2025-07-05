package com.quipuxmusic.core.application.usecase;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import com.quipuxmusic.core.domain.usecase.DeletePlaylistUseCase;
import com.quipuxmusic.core.domain.validator.PlaylistValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DeletePlaylistUseCaseImpl implements DeletePlaylistUseCase {
    
    private final PlaylistRepositoryPort playlistRepositoryPort;
    private final PlaylistValidator playlistValidator;
    
    @Autowired
    public DeletePlaylistUseCaseImpl(PlaylistRepositoryPort playlistRepositoryPort,
                                   PlaylistValidator playlistValidator) {
        this.playlistRepositoryPort = playlistRepositoryPort;
        this.playlistValidator = playlistValidator;
    }
    
    @Override
    public MessageResponseDTO execute(String name) {
        if (!playlistRepositoryPort.existsByName(name)) {
            throw new IllegalArgumentException("No se encontró la lista de reproducción con el nombre '" + name + "'");
        }
        playlistValidator.validateExists(name);
        playlistRepositoryPort.deleteByName(name);
        return new MessageResponseDTO("Lista eliminada exitosamente", "EXITO");
    }
} 