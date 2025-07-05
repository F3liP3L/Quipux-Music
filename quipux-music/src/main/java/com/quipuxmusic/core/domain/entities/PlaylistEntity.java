package com.quipuxmusic.core.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
public class PlaylistEntity {
    
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
    private List<SongEntity> songEntities = new ArrayList<>();

    public PlaylistEntity() {}

    public PlaylistEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PlaylistEntity(String name, String description, List<SongEntity> songEntities) {
        this.name = name;
        this.description = description;
        this.songEntities = songEntities != null ? songEntities : new ArrayList<>();
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
    
    public List<SongEntity> getSongs() {
        return songEntities;
    }
    
    public void setSongs(List<SongEntity> songEntities) {
        this.songEntities = songEntities != null ? songEntities : new ArrayList<>();
    }

    public void addSong(SongEntity songEntity) {
        if (songEntity != null && !songEntities.contains(songEntity)) {
            songEntities.add(songEntity);
        }
    }
    
    public void removeSong(SongEntity songEntity) {
        songEntities.remove(songEntity);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistEntity playlistEntity = (PlaylistEntity) o;
        return name.equals(playlistEntity.name);
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
                ", songsCount=" + (songEntities != null ? songEntities.size() : 0) +
                '}';
    }
} 