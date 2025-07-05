package com.quipuxmusic.infrastructure.adapter.primary.controller;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.application.facade.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class PlaylistController {

    private final CreatePlaylistFacadePort createPlaylistFacade;
    private final GetAllPlaylistFacadePort getAllPlaylistsFacade;
    private final GetPlaylistByNameFacadePort getPlaylistByNameFacade;
    private final DeletePlaylistFacadePort deletePlaylistFacade;

    @Autowired
    public PlaylistController(CreatePlaylistFacade createPlaylistFacade,
                            GetAllPlaylistsFacade getAllPlaylistsFacade,
                            GetPlaylistByNameFacade getPlaylistByNameFacade,
                            DeletePlaylistFacade deletePlaylistFacade) {
        this.createPlaylistFacade = createPlaylistFacade;
        this.getAllPlaylistsFacade = getAllPlaylistsFacade;
        this.getPlaylistByNameFacade = getPlaylistByNameFacade;
        this.deletePlaylistFacade = deletePlaylistFacade;
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        try {
            PlaylistDTO created = createPlaylistFacade.execute(playlistDTO);
            String encodedName = URLEncoder.encode(created.getNombre(), StandardCharsets.UTF_8);
            return ResponseEntity.created(URI.create("/lists/" + encodedName)).body(created);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO("Error al crear lista: " + e.getMessage(), "ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists() {
        List<PlaylistDTO> playlists = getAllPlaylistsFacade.execute();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<?> getPlaylist(@PathVariable String listName) {
        try {
            String decodedName = URLDecoder.decode(listName, StandardCharsets.UTF_8);
            PlaylistDTO playlist = getPlaylistByNameFacade.execute(decodedName);
            return ResponseEntity.ok(playlist);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO("Error al buscar lista: " + e.getMessage(), "ERROR");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String listName) {
        try {
            String decodedName = URLDecoder.decode(listName, StandardCharsets.UTF_8);
            MessageResponseDTO response = deletePlaylistFacade.execute(decodedName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO("Error al eliminar lista: " + e.getMessage(), "ERROR");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
} 