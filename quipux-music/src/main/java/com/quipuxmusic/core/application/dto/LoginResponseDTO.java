package com.quipuxmusic.core.application.dto;

public final class LoginResponseDTO {
    
    private String token;
    private String mensaje;
    private String nombreUsuario;
    private String rol;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String token) {
        this.token = token;
        this.mensaje = "Inicio de sesión exitoso";
    }

    public LoginResponseDTO(String token, String nombreUsuario, String rol) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.mensaje = "Inicio de sesión exitoso";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
} 