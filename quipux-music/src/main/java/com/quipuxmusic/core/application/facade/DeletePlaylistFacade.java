package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.domain.usecase.DeletePlaylistUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class DeletePlaylistFacade  implements DeletePlaylistFacadePort{
    
    private final DeletePlaylistUseCase deletePlaylistUseCase;
    
    @Autowired
    public DeletePlaylistFacade(DeletePlaylistUseCase deletePlaylistUseCase) {
        this.deletePlaylistUseCase = deletePlaylistUseCase;
    }
    @Override
    public MessageResponseDTO execute(String name) {
        return deletePlaylistUseCase.execute(name);
    }
} 