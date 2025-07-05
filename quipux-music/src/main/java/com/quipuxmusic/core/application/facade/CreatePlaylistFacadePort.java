package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.PlaylistDTO;

public interface CreatePlaylistFacadePort {
    PlaylistDTO execute(PlaylistDTO playlistDTO);
}