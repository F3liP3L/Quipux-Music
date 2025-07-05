package com.quipuxmusic.core.application.dto;

public class SongDTO {
    private String title;
    private String artist;
    private String album;
    private String year;
    private String genre;
    
    // Default constructor
    public SongDTO() {}
    
    // Constructor with all fields
    public SongDTO(String title, String artist, String album, String year, String genre) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.genre = genre;
    }
    
    // Getters and Setters
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
} 