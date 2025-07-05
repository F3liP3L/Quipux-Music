import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { Playlist } from '../../interfaces/playlist.interface';

@Component({
  selector: 'app-playlist-detail-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule],
  template: `
    <h2 mat-dialog-title>{{ data.nombre }}</h2>
    <mat-dialog-content>
      <p><strong>Descripción:</strong> {{ data.descripcion }}</p>
      <h3>Canciones ({{ data.canciones.length }})</h3>
      <div class="songs-list">
        <div *ngFor="let song of data.canciones; let i = index" class="song-item">
          <div class="song-info">
            <span class="song-number">{{ i + 1 }}.</span>
            <span class="song-title">{{ song.titulo }}</span>
            <span class="song-artist">{{ song.artista }}</span>
          </div>
          <div class="song-details">
            <span class="song-album">{{ song.album }} ({{ song.anno }})</span>
            <span class="song-genre">{{ song.genero }}</span>
          </div>
        </div>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cerrar</button>
    </mat-dialog-actions>
  `,
  styles: [`
    .songs-list {
      max-height: 400px;
      overflow-y: auto;
    }
    .song-item {
      padding: 12px;
      border-bottom: 1px solid #eee;
    }
    .song-info {
      display: flex;
      align-items: center;
      gap: 8px;
      margin-bottom: 4px;
    }
    .song-number {
      font-weight: bold;
      color: #666;
      min-width: 20px;
    }
    .song-title {
      font-weight: 600;
      color: #333;
    }
    .song-artist {
      color: #666;
    }
    .song-details {
      display: flex;
      gap: 16px;
      margin-left: 28px;
    }
    .song-album, .song-genre {
      font-size: 12px;
      color: #888;
    }
  `]
})
export class PlaylistDetailDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Playlist) {}
} 