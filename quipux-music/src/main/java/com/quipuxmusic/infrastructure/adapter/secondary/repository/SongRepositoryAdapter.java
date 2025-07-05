package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.domains.SongDomain;
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
    public SongDomain save(SongDomain songDomain) {
        SongEntity songEntity = toEntity(songDomain);
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
    private SongEntity toEntity(SongDomain songDomain) {
        if (songDomain == null) {
            return null;
        }
        
        SongEntity songEntity = new SongEntity();
        songEntity.setId(songDomain.getId());
        songEntity.setTitle(songDomain.getTitle());
        songEntity.setArtist(songDomain.getArtist());
        songEntity.setAlbum(songDomain.getAlbum());
        songEntity.setYear(songDomain.getYear());
        songEntity.setGenre(songDomain.getGenre());
        
        return songEntity;
    }
    
    private SongDomain toDomain(SongEntity songEntity) {
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