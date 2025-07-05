package com.quipuxmusic.infrastructure.adapter.primary.controller;

import com.quipuxmusic.core.application.dto.PlaylistDTO;
import com.quipuxmusic.core.application.dto.MessageResponseDTO;
import com.quipuxmusic.core.application.dto.SongDTO;
import com.quipuxmusic.core.application.facade.*;
import com.quipuxmusic.core.domain.exception.DuplicatePlaylistException;
import com.quipuxmusic.core.domain.exception.PlaylistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - PlaylistController")
class PlaylistControllerTest {

    @Mock
    private CreatePlaylistFacade createPlaylistFacade;

    @Mock
    private GetAllPlaylistsFacade getAllPlaylistsFacade;

    @Mock
    private GetPlaylistByNameFacade getPlaylistByNameFacade;

    @Mock
    private DeletePlaylistFacade deletePlaylistFacade;

    private PlaylistController playlistController;

    @BeforeEach
    void setUp() {
        playlistController = new PlaylistController(
            createPlaylistFacade,
            getAllPlaylistsFacade,
            getPlaylistByNameFacade,
            deletePlaylistFacade
        );
    }

    @Test
    @DisplayName("Debería crear playlist exitosamente")
    void shouldCreatePlaylistSuccessfully() throws Exception {
        PlaylistDTO request = new PlaylistDTO();
        request.setNombre("Mi Playlist");
        request.setDescripcion("Descripción de prueba");
        request.setCanciones(Collections.emptyList());

        PlaylistDTO createdPlaylist = new PlaylistDTO();
        createdPlaylist.setNombre("Mi Playlist");
        createdPlaylist.setDescripcion("Descripción de prueba");
        createdPlaylist.setCanciones(Collections.emptyList());

        when(createPlaylistFacade.execute(request)).thenReturn(createdPlaylist);
        ResponseEntity<?> response = playlistController.createPlaylist(request);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        PlaylistDTO responseBody = (PlaylistDTO) response.getBody();
        assertEquals("Mi Playlist", responseBody.getNombre());
        assertEquals("Descripción de prueba", responseBody.getDescripcion());

        String expectedLocation = "/lists/" + URLEncoder.encode("Mi Playlist", StandardCharsets.UTF_8);
        assertEquals(URI.create(expectedLocation), response.getHeaders().getLocation());

        verify(createPlaylistFacade).execute(request);
    }

    @Test
    @DisplayName("Debería manejar error al crear playlist duplicada")
    void shouldHandleErrorWhenCreatingDuplicatePlaylist() {
        PlaylistDTO request = new PlaylistDTO();
        request.setNombre("Playlist Existente");
        request.setDescripcion("Descripción");
        request.setCanciones(Collections.emptyList());

        when(createPlaylistFacade.execute(request))
            .thenThrow(new DuplicatePlaylistException("Ya existe una lista de reproducción con el nombre 'Playlist Existente'"));
        ResponseEntity<?> response = playlistController.createPlaylist(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error al crear lista: Ya existe una lista de reproducción con el nombre 'Playlist Existente'", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());

        verify(createPlaylistFacade).execute(request);
    }

    @Test
    @DisplayName("Debería manejar excepción genérica al crear playlist")
    void shouldHandleGenericExceptionWhenCreatingPlaylist() {
        PlaylistDTO request = new PlaylistDTO();
        request.setNombre("Mi Playlist");
        request.setDescripcion("Descripción");
        request.setCanciones(Collections.emptyList());

        when(createPlaylistFacade.execute(request))
            .thenThrow(new RuntimeException("Error interno del servidor"));

        ResponseEntity<?> response = playlistController.createPlaylist(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error al crear lista: Error interno del servidor", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());

        verify(createPlaylistFacade).execute(request);
    }

    @Test
    @DisplayName("Debería obtener todas las playlists exitosamente")
    void shouldGetAllPlaylistsSuccessfully() {
        PlaylistDTO playlist1 = new PlaylistDTO();
        playlist1.setNombre("Playlist 1");
        playlist1.setDescripcion("Descripción 1");
        playlist1.setCanciones(Collections.emptyList());

        PlaylistDTO playlist2 = new PlaylistDTO();
        playlist2.setNombre("Playlist 2");
        playlist2.setDescripcion("Descripción 2");
        playlist2.setCanciones(Collections.emptyList());

        List<PlaylistDTO> expectedPlaylists = Arrays.asList(playlist1, playlist2);

        when(getAllPlaylistsFacade.execute()).thenReturn(expectedPlaylists);

        ResponseEntity<List<PlaylistDTO>> response = playlistController.getAllPlaylists();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<PlaylistDTO> responseBody = response.getBody();
        assertEquals(2, responseBody.size());
        assertEquals("Playlist 1", responseBody.get(0).getNombre());
        assertEquals("Playlist 2", responseBody.get(1).getNombre());

        verify(getAllPlaylistsFacade).execute();
    }

    @Test
    @DisplayName("Debería obtener lista vacía cuando no hay playlists")
    void shouldGetEmptyListWhenNoPlaylists() {
        when(getAllPlaylistsFacade.execute()).thenReturn(Collections.emptyList());

        ResponseEntity<List<PlaylistDTO>> response = playlistController.getAllPlaylists();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(getAllPlaylistsFacade).execute();
    }

    @Test
    @DisplayName("Debería obtener playlist por nombre exitosamente")
    void shouldGetPlaylistByNameSuccessfully() throws Exception {
        String listName = "Mi Playlist";
        String encodedName = URLEncoder.encode(listName, StandardCharsets.UTF_8);

        PlaylistDTO expectedPlaylist = new PlaylistDTO();
        expectedPlaylist.setNombre("Mi Playlist");
        expectedPlaylist.setDescripcion("Descripción de prueba");
        expectedPlaylist.setCanciones(Collections.emptyList());

        when(getPlaylistByNameFacade.execute(listName)).thenReturn(expectedPlaylist);
        ResponseEntity<?> response = playlistController.getPlaylist(encodedName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        PlaylistDTO responseBody = (PlaylistDTO) response.getBody();
        assertEquals("Mi Playlist", responseBody.getNombre());
        assertEquals("Descripción de prueba", responseBody.getDescripcion());
        verify(getPlaylistByNameFacade).execute(listName);
    }

    @Test
    @DisplayName("Debería manejar error cuando playlist no existe")
    void shouldHandleErrorWhenPlaylistNotFound() {
        String listName = "Playlist Inexistente";
        String encodedName = URLEncoder.encode(listName, StandardCharsets.UTF_8);

        when(getPlaylistByNameFacade.execute(listName))
            .thenThrow(new PlaylistNotFoundException("No se encontró la lista de reproducción"));

        ResponseEntity<?> response = playlistController.getPlaylist(encodedName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error al buscar lista: No se encontró la lista de reproducción", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());
        verify(getPlaylistByNameFacade).execute(listName);
    }

    @Test
    @DisplayName("Debería manejar excepción genérica al buscar playlist")
    void shouldHandleGenericExceptionWhenGettingPlaylist() {
        String listName = "Mi Playlist";
        String encodedName = URLEncoder.encode(listName, StandardCharsets.UTF_8);

        when(getPlaylistByNameFacade.execute(listName))
            .thenThrow(new RuntimeException("Error interno del servidor"));

        ResponseEntity<?> response = playlistController.getPlaylist(encodedName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error al buscar lista: Error interno del servidor", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());

        verify(getPlaylistByNameFacade).execute(listName);
    }

    @Test
    @DisplayName("Debería eliminar playlist exitosamente")
    void shouldDeletePlaylistSuccessfully() {
        String listName = "Mi Playlist";
        String encodedName = URLEncoder.encode(listName, StandardCharsets.UTF_8);

        MessageResponseDTO expectedResponse = new MessageResponseDTO();
        expectedResponse.setMensaje("Lista de reproducción eliminada exitosamente");
        expectedResponse.setTipo("EXITO");

        when(deletePlaylistFacade.execute(listName)).thenReturn(expectedResponse);
        ResponseEntity<?> response = playlistController.deletePlaylist(encodedName);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(deletePlaylistFacade).execute(listName);
    }

    @Test
    @DisplayName("Debería manejar error al eliminar playlist inexistente")
    void shouldHandleErrorWhenDeletingNonExistentPlaylist() throws Exception {
        String listName = "Playlist Inexistente";
        String encodedName = URLEncoder.encode(listName, StandardCharsets.UTF_8);

        when(deletePlaylistFacade.execute(listName))
            .thenThrow(new PlaylistNotFoundException("No se encontró la lista de reproducción"));

        ResponseEntity<?> response = playlistController.deletePlaylist(encodedName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error al eliminar lista: No se encontró la lista de reproducción", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());

        verify(deletePlaylistFacade).execute(listName);
    }

    @Test
    @DisplayName("Debería manejar excepción genérica al eliminar playlist")
    void shouldHandleGenericExceptionWhenDeletingPlaylist() throws Exception {
        String listName = "Mi Playlist";
        String encodedName = URLEncoder.encode(listName, StandardCharsets.UTF_8);

        when(deletePlaylistFacade.execute(listName))
            .thenThrow(new RuntimeException("Error interno del servidor"));

        ResponseEntity<?> response = playlistController.deletePlaylist(encodedName);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        MessageResponseDTO responseBody = (MessageResponseDTO) response.getBody();
        assertEquals("Error al eliminar lista: Error interno del servidor", responseBody.getMensaje());
        assertEquals("ERROR", responseBody.getTipo());
        verify(deletePlaylistFacade).execute(listName);
    }

    @Test
    @DisplayName("Debería manejar caracteres especiales en nombres de playlist")
    void shouldHandleSpecialCharactersInPlaylistNames() throws Exception {
        String listName = "Playlist con ñ y áéíóú";
        String encodedName = URLEncoder.encode(listName, StandardCharsets.UTF_8);

        PlaylistDTO expectedPlaylist = new PlaylistDTO();
        expectedPlaylist.setNombre(listName);
        expectedPlaylist.setDescripcion("Descripción con caracteres especiales");
        expectedPlaylist.setCanciones(Collections.emptyList());

        when(getPlaylistByNameFacade.execute(listName)).thenReturn(expectedPlaylist);
        ResponseEntity<?> response = playlistController.getPlaylist(encodedName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        PlaylistDTO responseBody = (PlaylistDTO) response.getBody();
        assertEquals(listName, responseBody.getNombre());
        verify(getPlaylistByNameFacade).execute(listName);
    }
} 