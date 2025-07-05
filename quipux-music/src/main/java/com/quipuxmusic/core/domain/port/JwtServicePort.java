package com.quipuxmusic.core.domain.port;

import com.quipuxmusic.core.domain.domains.User;
import io.jsonwebtoken.Claims;

public interface JwtServicePort {
    
    String generateToken(User user);
    
    Claims validateToken(String token);
} 