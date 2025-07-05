import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, FormBuilder, FormArray, Validators, ReactiveFormsModule, AbstractControl, ValidatorFn } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ListService } from '../../service/listService';
import { HttpErrorResponse } from '@angular/common/http';

function yearRangeValidator(minYear: number): ValidatorFn {
  return (control: AbstractControl) => {
    const value = control.value;
    const currentYear = new Date().getFullYear();
    if (value) {
      if (Number(value) > currentYear) {
        return { maxYear: true };
      }
      if (Number(value) < minYear) {
        return { minYear: true };
      }
    }
    return null;
  };
}

@Component({
  selector: 'app-create-playlist-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule
  ],
  template: `
    <h2 mat-dialog-title>Crear Nueva Lista de Reproducción</h2>
    <mat-dialog-content>
      <form [formGroup]="playlistForm">
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Nombre de la Lista</mat-label>
          <input matInput formControlName="nombre" placeholder="Ej: Mi Lista Favorita">
          <mat-error *ngIf="playlistForm.get('nombre')?.hasError('required')">
            El nombre es obligatorio
          </mat-error>
          <mat-error *ngIf="playlistForm.get('nombre')?.hasError('minlength')">
            El nombre debe tener al menos 3 caracteres
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Descripción</mat-label>
          <textarea matInput formControlName="descripcion" placeholder="Describe tu lista"></textarea>
          <mat-error *ngIf="playlistForm.get('descripcion')?.hasError('required')">
            La descripción es obligatoria
          </mat-error>
          <mat-error *ngIf="playlistForm.get('descripcion')?.hasError('minlength')">
            La descripción debe tener al menos 5 caracteres
          </mat-error>
        </mat-form-field>

        <h3>Canciones</h3>
        <div formArrayName="canciones">
          <div *ngFor="let song of cancionesArray.controls; let i = index" [formGroupName]="i" class="song-form">
            <div class="song-header">
              <h4>Canción {{ i + 1 }}</h4>
              <button mat-icon-button type="button" (click)="removeSong(i)" color="warn">
                <mat-icon>delete</mat-icon>
              </button>
            </div>
            <div class="song-fields">
              <mat-form-field appearance="outline">
                <mat-label>Título</mat-label>
                <input matInput formControlName="titulo" placeholder="Título de la canción">
                <mat-error *ngIf="song.get('titulo')?.hasError('required')">
                  El título es obligatorio
                </mat-error>
                <mat-error *ngIf="song.get('titulo')?.hasError('minlength')">
                  El título debe tener al menos 2 caracteres
                </mat-error>
              </mat-form-field>
              <mat-form-field appearance="outline">
                <mat-label>Artista</mat-label>
                <input matInput formControlName="artista" placeholder="Nombre del artista">
                <mat-error *ngIf="song.get('artista')?.hasError('required')">
                  El artista es obligatorio
                </mat-error>
                <mat-error *ngIf="song.get('artista')?.hasError('minlength')">
                  El artista debe tener al menos 2 caracteres
                </mat-error>
              </mat-form-field>
              <mat-form-field appearance="outline">
                <mat-label>Álbum</mat-label>
                <input matInput formControlName="album" placeholder="Nombre del álbum">
                <mat-error *ngIf="song.get('album')?.hasError('required')">
                  El álbum es obligatorio
                </mat-error>
                <mat-error *ngIf="song.get('album')?.hasError('minlength')">
                  El álbum debe tener al menos 2 caracteres
                </mat-error>
              </mat-form-field>
              <mat-form-field appearance="outline">
                <mat-label>Año</mat-label>
                <input matInput formControlName="anno" placeholder="Año" maxlength="4">
                <mat-error *ngIf="song.get('anno')?.hasError('required')">
                  El año es obligatorio
                </mat-error>
                <mat-error *ngIf="song.get('anno')?.hasError('pattern')">
                  El año debe ser un número de 4 dígitos
                </mat-error>
                <mat-error *ngIf="song.get('anno')?.hasError('maxYear')">
                  El año no puede ser mayor al actual
                </mat-error>
                <mat-error *ngIf="song.get('anno')?.hasError('minYear')">
                  El año no puede ser menor a 1900
                </mat-error>
              </mat-form-field>
              <mat-form-field appearance="outline">
                <mat-label>Género</mat-label>
                <input matInput formControlName="genero" placeholder="Género musical">
                <mat-error *ngIf="song.get('genero')?.hasError('required')">
                  El género es obligatorio
                </mat-error>
                <mat-error *ngIf="song.get('genero')?.hasError('minlength')">
                  El género debe tener al menos 3 caracteres
                </mat-error>
              </mat-form-field>
            </div>
          </div>
        </div>
        <button mat-button type="button" (click)="addSong()" class="add-song-btn">
          <mat-icon>add</mat-icon>
          Agregar Canción
        </button>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button mat-raised-button color="primary" (click)="onSubmit()" [disabled]="!playlistForm.valid">
        Crear Lista
      </button>
    </mat-dialog-actions>
  `,
  styles: [`
    .full-width { width: 100%; margin-bottom: 16px; }
    .song-form { margin-bottom: 24px; padding: 16px; border: 1px solid #ddd; border-radius: 8px; }
    .song-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; }
    .song-fields { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
    .add-song-btn { margin-top: 16px; }
  `]
})
export class CreatePlaylistDialog implements OnInit {
  playlistForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private _listService: ListService,
    private _snackBar: MatSnackBar,
    private _dialogRef: MatDialogRef<CreatePlaylistDialog>
  ) {
    this.playlistForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.required, Validators.minLength(5)]],
      canciones: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.addSong(); // Agregar primera canción por defecto
  }

  get cancionesArray() {
    return this.playlistForm.get('canciones') as FormArray;
  }

  addSong(): void {
    const songGroup = this.fb.group({
      titulo: ['', [Validators.required, Validators.minLength(2)]],
      artista: ['', [Validators.required, Validators.minLength(2)]],
      album: ['', [Validators.required, Validators.minLength(2)]],
      anno: ['', [Validators.required, Validators.pattern(/^[0-9]{4}$/), yearRangeValidator(1900)]],
      genero: ['', [Validators.required, Validators.minLength(3)]]
    });
    this.cancionesArray.push(songGroup);
  }

  removeSong(index: number): void {
    if (this.cancionesArray.length > 1) {
      this.cancionesArray.removeAt(index);
    }
  }

  onSubmit(): void {
    if (this.playlistForm.valid) {
      this._listService.create(this.playlistForm.value).subscribe({
        next: () => {
          this._snackBar.open('Lista creada exitosamente', 'Cerrar', { duration: 3000 });
          this._dialogRef.close(true);
        },
        error: (error: HttpErrorResponse) => {
          const mensaje = 'Ya existe una lista con ese nombre. Por favor, elige un nombre diferente.';
          this._snackBar.open(mensaje, 'Cerrar', { duration: 4000 });
        }
      });
    }
  }
} 