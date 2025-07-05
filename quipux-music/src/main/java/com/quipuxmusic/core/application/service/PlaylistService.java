package com.quipuxmusic.core.application.service;

import com.quipuxmusic.core.domain.entities.Playlist;
import com.quipuxmusic.core.domain.entities.Song;
import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.dto.SongDTO;
import com.quipuxmusic.infrastructure.adapter.secondary.repository.PlaylistRepository;
import com.quipuxmusic.infrastructure.adapter.secondary.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    
    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }
    
    public PlaylistDTO createPlaylist(PlaylistDTO playlistDTO) {
        if (playlistDTO.getName() == null || playlistDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la lista de reproducción no puede ser nulo o vacío");
        }
        
        if (playlistRepository.existsByName(playlistDTO.getName())) {
            throw new IllegalArgumentException("Ya existe una lista de reproducción con el nombre '" + playlistDTO.getName() + "'");
        }
        
        Playlist playlist = new Playlist(playlistDTO.getName(), playlistDTO.getDescription());
        
        // Add songs if provided
        if (playlistDTO.getSongs() != null) {
            for (SongDTO songDTO : playlistDTO.getSongs()) {
                Song song = new Song(songDTO.getTitle(), songDTO.getArtist(), 
                                   songDTO.getAlbum(), songDTO.getYear(), songDTO.getGenre());
                song = songRepository.save(song);
                playlist.addSong(song);
            }
        }
        
        playlist = playlistRepository.save(playlist);
        return convertToDTO(playlist);
    }
    
    public List<PlaylistDTO> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public PlaylistDTO getPlaylistByName(String name) {
        Optional<Playlist> playlist = playlistRepository.findByName(name);
        if (playlist.isPresent()) {
            return convertToDTO(playlist.get());
        } else {
            throw new IllegalArgumentException("No se encontró la lista de reproducción con el nombre '" + name + "'");
        }
    }
    
    public void deletePlaylist(String name) {
        if (!playlistRepository.existsByName(name)) {
            throw new IllegalArgumentException("No se encontró la lista de reproducción con el nombre '" + name + "'");
        }
        playlistRepository.deleteByName(name);
    }
    
    private PlaylistDTO convertToDTO(Playlist playlist) {
        List<SongDTO> songDTOs = playlist.getSongs().stream()
                .map(this::convertToSongDTO)
                .collect(Collectors.toList());
        
        return new PlaylistDTO(playlist.getName(), playlist.getDescription(), songDTOs);
    }
    
    private SongDTO convertToSongDTO(Song song) {
        return new SongDTO(song.getTitle(), song.getArtist(), song.getAlbum(), song.getYear(), song.getGenre());
    }
} 