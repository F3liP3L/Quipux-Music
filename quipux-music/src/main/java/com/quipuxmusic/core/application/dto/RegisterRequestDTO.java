package com.quipuxmusic.core.application.dto;

import jakarta.validation.constraints.NotBlank;

public final class RegisterRequestDTO {
    
    @NotBlank(message = "El nombre de usuario es requerido")
    private String nombreUsuario;
    
    @NotBlank(message = "La contraseña es requerida")
    private String contrasena;
    
    private String rol = "USUARIO";

    public RegisterRequestDTO() {}

    public RegisterRequestDTO(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public RegisterRequestDTO(String nombreUsuario, String contrasena, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
} 