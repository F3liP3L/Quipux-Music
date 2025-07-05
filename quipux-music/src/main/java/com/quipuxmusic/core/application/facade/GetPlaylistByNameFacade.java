package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.domain.usecase.GetPlaylistByNameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class GetPlaylistByNameFacade implements GetPlaylistByNameFacadePort{
    private final GetPlaylistByNameUseCase getPlaylistByNameUseCase;
    
    @Autowired
    public GetPlaylistByNameFacade(GetPlaylistByNameUseCase getPlaylistByNameUseCase) {
        this.getPlaylistByNameUseCase = getPlaylistByNameUseCase;
    }
    @Override
    public PlaylistDTO execute(String name) {
        return getPlaylistByNameUseCase.execute(name);
    }
} 