package com.quipuxmusic.core.domain.validator;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.dto.SongDTO;
import com.quipuxmusic.core.domain.exception.DuplicatePlaylistException;
import com.quipuxmusic.core.domain.port.PlaylistRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public final class PlaylistValidator {
    
    private final PlaylistRepositoryPort playlistRepositoryPort;
    
    @Autowired
    public PlaylistValidator(PlaylistRepositoryPort playlistRepositoryPort) {
        this.playlistRepositoryPort = playlistRepositoryPort;
    }
    
    public void validateCreatePlaylist(PlaylistDTO playlistDTO) {
        validateName(playlistDTO.getNombre());
        validateNotDuplicate(playlistDTO.getNombre());
        validateSongDates(playlistDTO.getCanciones());
    }
    
    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la lista de reproducción no puede ser nulo o vacío");
        }
    }
    
    public void validateNotDuplicate(String name) {
        if (playlistRepositoryPort.existsByName(name)) {
            throw new DuplicatePlaylistException("Ya existe una lista de reproducción con el nombre '" + name + "'");
        }
    }
    
    public void validateExists(String name) {
        if (!playlistRepositoryPort.existsByName(name)) {
            throw new IllegalArgumentException("No se encontró la lista de reproducción con el nombre '" + name + "'");
        }
    }
    
    public void validateSongDates(List<SongDTO> songs) {
        if (songs == null || songs.isEmpty()) {
            return; // No hay canciones para validar
        }
        
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        
        for (SongDTO song : songs) {
            if (song != null && song.getAnno() != null && !song.getAnno().trim().isEmpty()) {
                validateSongYear(song.getAnno(), currentYear, song.getTitulo());
            }
        }
    }
    
    private void validateSongYear(String yearString, int currentYear, String songTitle) {
        try {
            int year = Integer.parseInt(yearString.trim());
            
            if (year > currentYear) {
                throw new IllegalArgumentException(
                    "La fecha de la canción '" + songTitle + "' (" + year + ") no puede ser mayor al año actual (" + currentYear + ")"
                );
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "El año de la canción '" + songTitle + "' debe ser un número válido. Valor recibido: '" + yearString + "'"
            );
        }
    }
} 