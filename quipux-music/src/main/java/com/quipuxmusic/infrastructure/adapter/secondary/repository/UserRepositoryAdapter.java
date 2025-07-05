package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.domains.UserDomain;
import com.quipuxmusic.core.domain.entities.UserEntity;
import com.quipuxmusic.core.domain.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDomain save(UserDomain userDomain) {
        UserEntity userEntity = toEntity(userDomain);
        userEntity = userRepository.save(userEntity);
        return toDomain(userEntity);
    }
    
    @Override
    public Optional<UserDomain> findByUsername(String username) {
        Optional<UserEntity> userEntityOpt = userRepository.findByUsername(username);
        return userEntityOpt.map(this::toDomain);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private UserEntity toEntity(UserDomain userDomain) {
        if (userDomain == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDomain.getId());
        userEntity.setUsername(userDomain.getUsername());
        userEntity.setPassword(userDomain.getPassword());
        userEntity.setRole(userDomain.getRole());
        
        return userEntity;
    }
    
    private UserDomain toDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        
        return new UserDomain(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getPassword(),
            userEntity.getRole()
        );
    }
} 