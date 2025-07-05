package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.domains.Song;
import com.quipuxmusic.core.domain.entities.SongEntity;
import com.quipuxmusic.core.domain.port.SongRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SongRepositoryAdapter implements SongRepositoryPort {
    
    private final SongRepository songRepository;
    
    @Autowired
    public SongRepositoryAdapter(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    
    @Override
    public Song save(Song song) {
        SongEntity songEntity = toEntity(song);
        songEntity = songRepository.save(songEntity);
        return toDomain(songEntity);
    }
    
    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return songRepository.existsById(id);
    }
    
    // Métodos privados para conversión directa
    private SongEntity toEntity(Song song) {
        if (song == null) {
            return null;
        }
        
        SongEntity songEntity = new SongEntity();
        songEntity.setId(song.getId());
        songEntity.setTitle(song.getTitle());
        songEntity.setArtist(song.getArtist());
        songEntity.setAlbum(song.getAlbum());
        songEntity.setYear(song.getYear());
        songEntity.setGenre(song.getGenre());
        
        return songEntity;
    }
    
    private Song toDomain(SongEntity songEntity) {
        if (songEntity == null) {
            return null;
        }
        
        return new Song(
            songEntity.getId(),
            songEntity.getTitle(),
            songEntity.getArtist(),
            songEntity.getAlbum(),
            songEntity.getYear(),
            songEntity.getGenre()
        );
    }
} 