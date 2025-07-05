package com.quipuxmusic.infrastructure.adapter.primary.controller;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.service.PlaylistService;
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

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        try {
            PlaylistDTO created = playlistService.createPlaylist(playlistDTO);
            String encodedName = URLEncoder.encode(created.getName(), StandardCharsets.UTF_8);
            return ResponseEntity.created(URI.create("/lists/" + encodedName)).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear lista: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists() {
        List<PlaylistDTO> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<?> getPlaylist(@PathVariable String listName) {
        try {
            String decodedName = URLDecoder.decode(listName, StandardCharsets.UTF_8);
            PlaylistDTO playlist = playlistService.getPlaylistByName(decodedName);
            return ResponseEntity.ok(playlist);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al buscar lista: " + e.getMessage());
        }
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String listName) {
        try {
            String decodedName = URLDecoder.decode(listName, StandardCharsets.UTF_8);
            playlistService.deletePlaylist(decodedName);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar lista: " + e.getMessage());
        }
    }
} 