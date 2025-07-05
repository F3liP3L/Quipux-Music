package com.quipuxmusic.core.application.dto;

import java.util.List;

public final class PlaylistDTO {
    private String nombre;
    private String descripcion;
    private List<SongDTO> canciones;

    public PlaylistDTO() {}

    public PlaylistDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public PlaylistDTO(String nombre, String descripcion, List<SongDTO> canciones) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.canciones = canciones;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public List<SongDTO> getCanciones() {
        return canciones;
    }
    
    public void setCanciones(List<SongDTO> canciones) {
        this.canciones = canciones;
    }
} 