export interface Cancion {
  titulo: string;
  artista: string;
  album: string;
  anno: string;
  genero: string;
}

export interface Playlist {
  nombre: string;
  descripcion: string;
  canciones: Cancion[];
}

export interface PlaylistSummary {
  nombre: string;
  descripcion: string;
}

export interface CreatePlaylistRequest {
  nombre: string;
  descripcion: string;
  canciones: Cancion[];
} 