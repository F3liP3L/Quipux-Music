package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.domains.PlaylistDomain;
import com.quipuxmusic.core.domain.domains.SongDomain;
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
    public PlaylistDomain save(PlaylistDomain playlistDomain) {
        PlaylistEntity playlistEntity = toEntity(playlistDomain);
        playlistEntity = playlistRepository.save(playlistEntity);
        return toDomain(playlistEntity);
    }
    
    @Override
    public List<PlaylistDomain> findAll() {
        return playlistRepository.findAll().stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public Optional<PlaylistDomain> findByName(String name) {
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
    private PlaylistEntity toEntity(PlaylistDomain playlistDomain) {
        if (playlistDomain == null) {
            return null;
        }
        
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(playlistDomain.getId());
        playlistEntity.setName(playlistDomain.getName());
        playlistEntity.setDescription(playlistDomain.getDescription());
        
        if (playlistDomain.getSongs() != null) {
            List<com.quipuxmusic.core.domain.entities.SongEntity> songEntities = playlistDomain.getSongs().stream()
                    .map(this::toSongEntity)
                    .collect(java.util.stream.Collectors.toList());
            playlistEntity.setSongs(songEntities);
        }
        
        return playlistEntity;
    }
    
    private PlaylistDomain toDomain(PlaylistEntity playlistEntity) {
        if (playlistEntity == null) {
            return null;
        }
        
        List<SongDomain> songDomains = null;
        if (playlistEntity.getSongs() != null) {
            songDomains = playlistEntity.getSongs().stream()
                    .map(this::toSongDomain)
                    .collect(java.util.stream.Collectors.toList());
        }
        
        return new PlaylistDomain(
            playlistEntity.getId(),
            playlistEntity.getName(),
            playlistEntity.getDescription(),
                songDomains
        );
    }
    
    private com.quipuxmusic.core.domain.entities.SongEntity toSongEntity(SongDomain songDomain) {
        if (songDomain == null) {
            return null;
        }
        
        com.quipuxmusic.core.domain.entities.SongEntity songEntity = new com.quipuxmusic.core.domain.entities.SongEntity();
        songEntity.setId(songDomain.getId());
        songEntity.setTitle(songDomain.getTitle());
        songEntity.setArtist(songDomain.getArtist());
        songEntity.setAlbum(songDomain.getAlbum());
        songEntity.setYear(songDomain.getYear());
        songEntity.setGenre(songDomain.getGenre());
        
        return songEntity;
    }
    
    private SongDomain toSongDomain(com.quipuxmusic.core.domain.entities.SongEntity songEntity) {
        if (songEntity == null) {
            return null;
        }
        
        return new SongDomain(
            songEntity.getId(),
            songEntity.getTitle(),
            songEntity.getArtist(),
            songEntity.getAlbum(),
            songEntity.getYear(),
            songEntity.getGenre()
        );
    }
} 