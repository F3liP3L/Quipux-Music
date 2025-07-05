export interface LoginRequest {
  nombreUsuario: string;
  contrasena: string;
}

export interface RegisterRequest {
  nombreUsuario: string;
  contrasena: string;
  rol: 'USUARIO' | 'ADMIN';
}

export interface AuthResponse {
  token: string;
  nombreUsuario: string;
  rol: string;
}

export interface User {
  nombreUsuario: string;
  rol: string;
} 