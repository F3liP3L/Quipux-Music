import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
import { debounceTime, distinctUntilChanged, switchMap, tap, catchError } from 'rxjs/operators';
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
    private _snackBar: MatSnackBar,
    private cdr: ChangeDetectorRef
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
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error fetching lists:', error);
        this._snackBar.open('Error al cargar las listas', 'Cerrar', { duration: 3000 });
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  private setupSearch(): void {
    this.searchControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      tap(() => { this.isLoading = true; this.cdr.detectChanges(); }),
      switchMap(searchTerm => {
        if (searchTerm && searchTerm.trim()) {
          return this._listService.getByName(searchTerm.trim()).pipe(
            catchError(() => {
              this._snackBar.open('Lista no encontrada', 'Cerrar', { duration: 3000 });
              return [null];
            })
          );
        } else {
          return this._listService.getAll();
        }
      })
    ).subscribe((result) => {
      if (Array.isArray(result)) {
        this.lists = result;
      } else if (result) {
        this.lists = [{
          nombre: result.nombre,
          descripcion: result.descripcion
        }];
      } else {
        this.lists = [];
      }
      this.isLoading = false;
      this.cdr.detectChanges();
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
      this.isLoading = true;
      this._listService.delete(playlist.nombre).subscribe({
        next: () => {
          this._snackBar.open('Lista eliminada exitosamente', 'Cerrar', { duration: 3000 });
          this.loadLists();
        },
        error: (error) => {
          this._snackBar.open('Error al eliminar la lista', 'Cerrar', { duration: 3000 });
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }
  }

  protected onCreatePlaylist(): void {
    const dialogRef = this._dialog.open(CreatePlaylistDialog, {
      width: '800px'
    });
    dialogRef.afterClosed().subscribe(result => {
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
