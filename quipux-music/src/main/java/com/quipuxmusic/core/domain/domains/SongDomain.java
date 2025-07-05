package com.quipuxmusic.core.domain.domains;

import java.util.Objects;

public final class SongDomain {
    
    private Long id;
    private String title;
    private String artist;
    private String album;
    private String year;
    private String genre;
    
    public SongDomain() {}
    
    public SongDomain(String title, String artist, String album, String year, String genre) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.genre = genre;
    }
    
    public SongDomain(Long id, String title, String artist, String album, String year, String genre) {
        this.id = id;
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
        
        SongDomain songDomain = (SongDomain) o;
        
        if (!Objects.equals(title, songDomain.title)) return false;
        if (!Objects.equals(artist, songDomain.artist)) return false;
        if (!Objects.equals(album, songDomain.album)) return false;
        if (!Objects.equals(year, songDomain.year)) return false;
        return Objects.equals(genre, songDomain.genre);
    }
    
    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", year='" + year + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
} 