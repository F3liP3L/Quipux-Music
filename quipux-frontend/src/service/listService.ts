import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Playlist, PlaylistSummary, CreatePlaylistRequest } from '../interfaces/playlist.interface';
import { environment } from '../environments/environment';
// import { MockDataService } from './mock-data.service';

@Injectable({providedIn: 'root'})
export class ListService {

  private http = inject(HttpClient);
  private baseUrl = environment.apiUrl;
  // private mockService = inject(MockDataService);

  // Usar mock temporalmente para pruebas
  private useMock = false;

  public getAll(): Observable<PlaylistSummary[]> {
    return this.http.get<PlaylistSummary[]>(`${this.baseUrl}/lists`);
  }

  public getByName(name: string): Observable<Playlist> {
    return this.http.get<Playlist>(`${this.baseUrl}/lists/${encodeURIComponent(name)}`);
  }

  public create(playlist: CreatePlaylistRequest): Observable<Playlist> {
    return this.http.post<Playlist>(`${this.baseUrl}/lists`, playlist);
  }

  public delete(name: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/lists/${encodeURIComponent(name)}`);
  }
}
