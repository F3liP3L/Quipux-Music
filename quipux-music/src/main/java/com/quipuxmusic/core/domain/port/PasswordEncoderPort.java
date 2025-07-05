package com.quipuxmusic.core.domain.port;

public interface PasswordEncoderPort {
    
    String encode(String rawPassword);
    
    boolean matches(String rawPassword, String encodedPassword);
} 