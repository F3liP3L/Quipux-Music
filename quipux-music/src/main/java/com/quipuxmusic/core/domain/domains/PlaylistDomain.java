package com.quipuxmusic.core.domain.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class PlaylistDomain {
    
    private Long id;
    private String name;
    private String description;
    private List<SongDomain> songDomains;
    
    public PlaylistDomain() {
        this.songDomains = new ArrayList<>();
    }
    
    public PlaylistDomain(String name, String description) {
        this.name = name;
        this.description = description;
        this.songDomains = new ArrayList<>();
    }
    
    public PlaylistDomain(String name, String description, List<SongDomain> songDomains) {
        this.name = name;
        this.description = description;
        this.songDomains = songDomains != null ? songDomains : new ArrayList<>();
    }
    
    public PlaylistDomain(Long id, String name, String description, List<SongDomain> songDomains) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.songDomains = songDomains != null ? songDomains : new ArrayList<>();
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
    
    public List<SongDomain> getSongs() {
        return songDomains;
    }
    
    public void setSongs(List<SongDomain> songDomains) {
        this.songDomains = songDomains != null ? songDomains : new ArrayList<>();
    }
    
    // Helper methods
    public void addSong(SongDomain songDomain) {
        if (songDomain != null && !songDomains.contains(songDomain)) {
            songDomains.add(songDomain);
        }
    }
    
    public void removeSong(SongDomain songDomain) {
        songDomains.remove(songDomain);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PlaylistDomain playlistDomain = (PlaylistDomain) o;
        
        return Objects.equals(name, playlistDomain.name);
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
                ", songsCount=" + (songDomains != null ? songDomains.size() : 0) +
                '}';
    }
} 