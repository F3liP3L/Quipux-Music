package com.quipuxmusic.core.application.dto;

public final class MessageResponseDTO {
    
    private String mensaje;
    private String tipo;
    private Object datos;

    public MessageResponseDTO() {}

    public MessageResponseDTO(String mensaje) {
        this.mensaje = mensaje;
        this.tipo = "INFO";
    }

    public MessageResponseDTO(String mensaje, String tipo) {
        this.mensaje = mensaje;
        this.tipo = tipo;
    }

    public MessageResponseDTO(String mensaje, String tipo, Object datos) {
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.datos = datos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }
} 