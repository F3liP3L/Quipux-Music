package com.quipuxmusic.core.domain.port;

import com.quipuxmusic.core.domain.domains.UserDomain;

import java.util.Optional;

public interface UserRepositoryPort {
    
    UserDomain save(UserDomain userDomain);
    
    Optional<UserDomain> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    void deleteById(Long id);
} 