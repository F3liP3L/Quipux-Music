package com.quipuxmusic.infrastructure.adapter.primary.controller;

import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.domain.exception.AuthenticationException;
import com.quipuxmusic.core.domain.exception.DuplicatePlaylistException;
import com.quipuxmusic.core.domain.exception.DuplicateUserException;
import com.quipuxmusic.core.domain.exception.PlaylistNotFoundException;
import com.quipuxmusic.core.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MessageResponseDTO> handleAuthenticationException(AuthenticationException e) {
        MessageResponseDTO error = new MessageResponseDTO("Error de autenticación: " + e.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<MessageResponseDTO> handleDuplicateUserException(DuplicateUserException e) {
        MessageResponseDTO error = new MessageResponseDTO("Error de registro: " + e.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DuplicatePlaylistException.class)
    public ResponseEntity<MessageResponseDTO> handleDuplicatePlaylistException(DuplicatePlaylistException e) {
        MessageResponseDTO error = new MessageResponseDTO("Error al crear lista: " + e.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PlaylistNotFoundException.class)
    public ResponseEntity<MessageResponseDTO> handlePlaylistNotFoundException(PlaylistNotFoundException e) {
        MessageResponseDTO error = new MessageResponseDTO("Error al buscar lista: " + e.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
        MessageResponseDTO error = new MessageResponseDTO("Error de usuario: " + e.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponseDTO> handleIllegalArgumentException(IllegalArgumentException e) {
        MessageResponseDTO error = new MessageResponseDTO("Error de validación: " + e.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponseDTO> handleGenericException(Exception e) {
        MessageResponseDTO error = new MessageResponseDTO("Error interno del servidor: " + e.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
} 