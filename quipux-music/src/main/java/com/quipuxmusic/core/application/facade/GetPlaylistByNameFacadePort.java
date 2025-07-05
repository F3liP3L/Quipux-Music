package com.quipuxmusic.core.application.facade;

import com.quipuxmusic.core.application.dto.PlaylistDTO;

public interface GetPlaylistByNameFacadePort {
    PlaylistDTO execute(String name);
}
