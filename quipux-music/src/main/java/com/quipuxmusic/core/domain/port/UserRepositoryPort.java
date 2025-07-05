package com.quipuxmusic.core.domain.port;

import com.quipuxmusic.core.domain.domains.User;

import java.util.Optional;

public interface UserRepositoryPort {
    
    User save(User user);
    
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    void deleteById(Long id);
} 