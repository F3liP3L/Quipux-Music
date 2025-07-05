import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse, User } from '../interfaces/auth.interface';
import { environment } from '../environments/environment';
import { StorageUtil } from '../utils/storage.util';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private baseUrl = environment.apiUrl;
  
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor() {
    this.loadUserFromStorage();
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/auth/login`, credentials)
      .pipe(
        tap(response => {
          this.setToken(response.token);
          this.setUser(response);
        })
      );
  }

  register(userData: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/auth/register`, userData)
      .pipe(
        tap(response => {
          this.setToken(response.token);
          this.setUser(response);
        })
      );
  }

  logout(): void {
    StorageUtil.removeItem('token');
    StorageUtil.removeItem('user');
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  getToken(): string | null {
    return StorageUtil.getItem('token');
  }

  private setToken(token: string): void {
    StorageUtil.setItem('token', token);
  }

  private setUser(authResponse: AuthResponse): void {
    const user: User = {
      nombreUsuario: authResponse.nombreUsuario,
      rol: authResponse.rol
    };
    StorageUtil.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  private loadUserFromStorage(): void {
    const userStr = StorageUtil.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.currentUserSubject.next(user);
    }
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }
} 