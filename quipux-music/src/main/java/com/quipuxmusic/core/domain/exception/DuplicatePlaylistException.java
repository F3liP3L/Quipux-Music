package com.quipuxmusic.core.domain.exception;

public class DuplicatePlaylistException extends RuntimeException {
    
    public DuplicatePlaylistException(String message) {
        super(message);
    }
    
    public DuplicatePlaylistException(String message, Throwable cause) {
        super(message, cause);
    }
} 