package com.quipuxmusic.core.domain.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Playlist {
    
    private Long id;
    private String name;
    private String description;
    private List<Song> songs;
    
    public Playlist() {
        this.songs = new ArrayList<>();
    }
    
    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
        this.songs = new ArrayList<>();
    }
    
    public Playlist(String name, String description, List<Song> songs) {
        this.name = name;
        this.description = description;
        this.songs = songs != null ? songs : new ArrayList<>();
    }
    
    public Playlist(Long id, String name, String description, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.songs = songs != null ? songs : new ArrayList<>();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public List<Song> getSongs() {
        return songs;
    }
    
    public void setSongs(List<Song> songs) {
        this.songs = songs != null ? songs : new ArrayList<>();
    }
    
    // Helper methods
    public void addSong(Song song) {
        if (song != null && !songs.contains(song)) {
            songs.add(song);
        }
    }
    
    public void removeSong(Song song) {
        songs.remove(song);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Playlist playlist = (Playlist) o;
        
        return Objects.equals(name, playlist.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", songsCount=" + (songs != null ? songs.size() : 0) +
                '}';
    }
} 