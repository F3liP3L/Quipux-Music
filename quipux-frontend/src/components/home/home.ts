import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';

import { ListService } from '../../service/listService';
import { AuthService } from '../../service/auth.service';
import { PlaylistSummary, Playlist } from '../../interfaces/playlist.interface';
import { User } from '../../interfaces/auth.interface';
import { PlaylistDetailDialog } from '../dialogs/playlist-detail-dialog';
import { CreatePlaylistDialog } from '../dialogs/create-playlist-dialog';

@Component({
  selector: 'app-home',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatDialogModule,
    MatToolbarModule,
    MatMenuModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit {
  protected lists: PlaylistSummary[] = [];
  protected searchControl = new FormControl('');
  protected isLoading = false;
  protected currentUser: User | null = null;

  constructor(
    private _listService: ListService,
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.currentUser = this._authService.getCurrentUser();
    this.loadLists();
    this.setupSearch();
  }

  private loadLists(): void {
    this.isLoading = true;
    this._listService.getAll().subscribe({
      next: (data: PlaylistSummary[]) => {
        this.lists = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error fetching lists:', error);
        this._snackBar.open('Error al cargar las listas', 'Cerrar', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  private setupSearch(): void {
    this.searchControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap(searchTerm => {
        if (searchTerm && searchTerm.trim()) {
          return this._listService.getByName(searchTerm.trim());
        } else {
          return of(null);
        }
      })
    ).subscribe({
      next: (playlist) => {
        if (playlist) {
          this.lists = [{
            nombre: playlist.nombre,
            descripcion: playlist.descripcion
          }];
        } else if (!this.searchControl.value) {
          this.loadLists();
        }
      },
      error: (error) => {
        this._snackBar.open('Lista no encontrada', 'Cerrar', { duration: 3000 });
        this.lists = [];
      }
    });
  }

  protected onViewDetails(playlist: PlaylistSummary): void {
    this._listService.getByName(playlist.nombre).subscribe({
      next: (fullPlaylist: Playlist) => {
        this._dialog.open(PlaylistDetailDialog, {
          width: '600px',
          data: fullPlaylist
        });
      },
      error: (error) => {
        this._snackBar.open('Error al cargar los detalles', 'Cerrar', { duration: 3000 });
      }
    });
  }

  protected onDeletePlaylist(playlist: PlaylistSummary): void {
    if (confirm(`¿Estás seguro de que quieres eliminar la lista "${playlist.nombre}"?`)) {
      this._listService.delete(playlist.nombre).subscribe({
        next: () => {
          this._snackBar.open('Lista eliminada exitosamente', 'Cerrar', { duration: 3000 });
          this.loadLists();
        },
        error: (error) => {
          this._snackBar.open('Error al eliminar la lista', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }

  protected onCreatePlaylist(): void {
    this._dialog.open(CreatePlaylistDialog, {
      width: '800px'
    }).afterClosed().subscribe(result => {
      if (result) {
        this.loadLists();
      }
    });
  }

  protected onLogout(): void {
    this._authService.logout();
    // Redirigir al login
    window.location.href = '/login';
  }

  protected clearSearch(): void {
    this.searchControl.setValue('');
  }
}
