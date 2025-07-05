package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.domain.usecase.GetAllPlaylistsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class GetAllPlaylistsFacade implements GetAllPlaylistFacadePort{
    private final GetAllPlaylistsUseCase getAllPlaylistsUseCase;
    
    @Autowired
    public GetAllPlaylistsFacade(GetAllPlaylistsUseCase getAllPlaylistsUseCase) {
        this.getAllPlaylistsUseCase = getAllPlaylistsUseCase;
    }
    @Override
    public List<PlaylistDTO> execute() {
        return getAllPlaylistsUseCase.execute();
    }
} 