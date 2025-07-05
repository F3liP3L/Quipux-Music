package com.quipuxmusic.core.domain.domains;

import java.util.Objects;

public final class UserDomain {
    
    private Long id;
    private String username;
    private String password;
    private String role;

    public UserDomain() {}

    public UserDomain(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "USER";
    }

    public UserDomain(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserDomain(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

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
        
        UserDomain userDomain = (UserDomain) o;
        
        return Objects.equals(username, userDomain.username);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(username);
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