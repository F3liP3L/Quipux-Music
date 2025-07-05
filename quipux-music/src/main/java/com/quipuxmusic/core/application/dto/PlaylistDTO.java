package com.quipuxmusic.core.application.dto;

import java.util.List;

public class PlaylistDTO {
    private String name;
    private String description;
    private List<SongDTO> songs;

    public PlaylistDTO() {}

    public PlaylistDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PlaylistDTO(String name, String description, List<SongDTO> songs) {
        this.name = name;
        this.description = description;
        this.songs = songs;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<SongDTO> getSongs() {
        return songs;
    }
    
    public void setSongs(List<SongDTO> songs) {
        this.songs = songs;
    }
} 