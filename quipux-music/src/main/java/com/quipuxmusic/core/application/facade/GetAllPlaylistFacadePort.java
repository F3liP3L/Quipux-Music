package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.PlaylistDTO;

import java.util.List;

public interface GetAllPlaylistFacadePort {
    List<PlaylistDTO> execute();
}