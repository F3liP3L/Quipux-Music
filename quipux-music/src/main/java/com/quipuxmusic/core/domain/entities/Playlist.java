package com.quipuxmusic.core.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
public class Playlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es requerido")
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "playlist_songs",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs = new ArrayList<>();

    public Playlist() {}

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Playlist(String name, String description, List<Song> songs) {
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
        
        return name.equals(playlist.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
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