package com.quipuxmusic.core.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "songs")
public class SongEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título es requerido")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "El artista es requerido")
    @Column(nullable = false)
    private String artist;
    
    @NotBlank(message = "El álbum es requerido")
    @Column(nullable = false)
    private String album;
    
    @NotBlank(message = "El año es requerido")
    @Column(name = "release_year", nullable = false)
    private String year;
    
    @NotBlank(message = "El género es requerido")
    @Column(nullable = false)
    private String genre;

    public SongEntity() {}

    public SongEntity(String title, String artist, String album, String year, String genre) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public String getAlbum() {
        return album;
    }
    
    public void setAlbum(String album) {
        this.album = album;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        SongEntity songEntity = (SongEntity) o;
        
        if (!title.equals(songEntity.title)) return false;
        if (!artist.equals(songEntity.artist)) return false;
        if (!album.equals(songEntity.album)) return false;
        if (!year.equals(songEntity.year)) return false;
        return genre.equals(songEntity.genre);
    }
    
    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + artist.hashCode();
        result = 31 * result + album.hashCode();
        result = 31 * result + year.hashCode();
        result = 31 * result + genre.hashCode();
        return result;
    }
} 