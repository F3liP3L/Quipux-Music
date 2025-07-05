package com.quipuxmusic.infrastructure.adapter.secondary.repository;

import com.quipuxmusic.core.domain.domains.User;
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
    public User save(User user) {
        UserEntity userEntity = toEntity(user);
        userEntity = userRepository.save(userEntity);
        return toDomain(userEntity);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
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

    private UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        
        return userEntity;
    }
    
    private User toDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        
        return new User(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getPassword(),
            userEntity.getRole()
        );
    }
} 