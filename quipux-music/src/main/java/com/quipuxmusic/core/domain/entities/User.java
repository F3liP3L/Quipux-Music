package com.quipuxmusic.core.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre de usuario es requerido")
    @Column(nullable = false, unique = true)
    private String username;
    
    @NotBlank(message = "La contraseña es requerida")
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role = "USER";
    
    // Default constructor
    public User() {}
    
    // Constructor with username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Constructor with all fields
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User) o;
        
        return username.equals(user.username);
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
} 