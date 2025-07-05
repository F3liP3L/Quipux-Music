package com.quipuxmusic.core.domain.port;

import com.quipuxmusic.core.domain.domains.UserDomain;
import io.jsonwebtoken.Claims;

public interface JwtServicePort {
    
    String generateToken(UserDomain userDomain);
    
    Claims validateToken(String token);
} 