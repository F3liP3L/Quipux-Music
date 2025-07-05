package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.domains.Playlist;
import com.quipuxmusic.core.domain.entities.PlaylistEntity;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlaylistRepositoryAdapter implements PlaylistRepositoryPort {
    
    private final PlaylistRepository playlistRepository;
    
    @Autowired
    public PlaylistRepositoryAdapter(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }
    
    @Override
    public Playlist save(Playlist playlist) {
        PlaylistEntity playlistEntity = toEntity(playlist);
        playlistEntity = playlistRepository.save(playlistEntity);
        return toDomain(playlistEntity);
    }
    
    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll().stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public Optional<Playlist> findByName(String name) {
        Optional<PlaylistEntity> playlistEntityOpt = playlistRepository.findByName(name);
        return playlistEntityOpt.map(this::toDomain);
    }
    
    @Override
    public boolean existsByName(String name) {
        return playlistRepository.existsByName(name);
    }
    
    @Override
    public void deleteByName(String name) {
        playlistRepository.deleteByName(name);
    }
    
    // Métodos privados para conversión directa
    private PlaylistEntity toEntity(Playlist playlist) {
        if (playlist == null) {
            return null;
        }
        
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(playlist.getId());
        playlistEntity.setName(playlist.getName());
        playlistEntity.setDescription(playlist.getDescription());
        
        if (playlist.getSongs() != null) {
            List<com.quipuxmusic.core.domain.entities.SongEntity> songEntities = playlist.getSongs().stream()
                    .map(this::toSongEntity)
                    .collect(java.util.stream.Collectors.toList());
            playlistEntity.setSongs(songEntities);
        }
        
        return playlistEntity;
    }
    
    private Playlist toDomain(PlaylistEntity playlistEntity) {
        if (playlistEntity == null) {
            return null;
        }
        
        List<com.quipuxmusic.core.domain.domains.Song> songs = null;
        if (playlistEntity.getSongs() != null) {
            songs = playlistEntity.getSongs().stream()
                    .map(this::toSongDomain)
                    .collect(java.util.stream.Collectors.toList());
        }
        
        return new Playlist(
            playlistEntity.getId(),
            playlistEntity.getName(),
            playlistEntity.getDescription(),
            songs
        );
    }
    
    private com.quipuxmusic.core.domain.entities.SongEntity toSongEntity(com.quipuxmusic.core.domain.domains.Song song) {
        if (song == null) {
            return null;
        }
        
        com.quipuxmusic.core.domain.entities.SongEntity songEntity = new com.quipuxmusic.core.domain.entities.SongEntity();
        songEntity.setId(song.getId());
        songEntity.setTitle(song.getTitle());
        songEntity.setArtist(song.getArtist());
        songEntity.setAlbum(song.getAlbum());
        songEntity.setYear(song.getYear());
        songEntity.setGenre(song.getGenre());
        
        return songEntity;
    }
    
    private com.quipuxmusic.core.domain.domains.Song toSongDomain(com.quipuxmusic.core.domain.entities.SongEntity songEntity) {
        if (songEntity == null) {
            return null;
        }
        
        return new com.quipuxmusic.core.domain.domains.Song(
            songEntity.getId(),
            songEntity.getTitle(),
            songEntity.getArtist(),
            songEntity.getAlbum(),
            songEntity.getYear(),
            songEntity.getGenre()
        );
    }
} 