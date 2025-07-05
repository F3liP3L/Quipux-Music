# Quipux Music Frontend

Quipux Music Frontend es una aplicación web desarrollada en Angular 20 y Angular Material para la gestión de listas de reproducción musicales. Permite a los usuarios autenticarse, crear, buscar, visualizar y eliminar listas de reproducción, así como ver los detalles de las canciones de cada lista.

## Características principales

- Autenticación JWT segura (login y protección de rutas)
- Visualización de todas las listas de reproducción en cards
- Búsqueda en tiempo real por nombre de lista
- Modal para ver detalles de canciones de una lista
- Formulario dinámico para crear nuevas listas y añadir canciones
- Eliminación de listas con confirmación
- Interfaz responsive y moderna con Angular Material
- Validación completa de formularios
- Manejo de errores y mensajes claros al usuario

## Tecnologías utilizadas

- Angular 20
- Angular Material
- TypeScript
- RxJS
- SCSS
- JWT (autenticación)

## Requisitos previos

- Node.js (versión 18 o superior)
- npm o yarn
- Backend Quipux Music API ejecutándose en `http://localhost:8080` (o la URL que configures)

## Instalación y ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/F3liP3L/Quipux-Music.git
   cd quipux-frontend
   ```
2. Instala las dependencias:
   ```bash
   npm install
   ```
3. Configura la URL del backend en `src/environments/environment.ts` si es necesario.
4. Ejecuta la aplicación:
   ```bash
   ng serve
   ```
5. Abre tu navegador en `http://localhost:4200`

## Estructura del proyecto

```
src/
├── app/                  # Configuración principal
├── components/           # Componentes de la aplicación
│   ├── home/             # Página principal con listas
│   └── login/            # Formulario de autenticación
├── dialogs/              # Modales para detalles y creación de listas
├── interfaces/           # Interfaces TypeScript
│   ├── auth.interface.ts
│   └── playlist.interface.ts
├── service/              # Servicios de datos y autenticación
│   ├── auth.service.ts
│   └── listService.ts
├── router/               # Configuración de rutas y guards
│   └── guards/           # Guards de autenticación
├── interceptor/          # Interceptores HTTP
├── environments/         # Configuración de entorno
└── utils/                # Utilidades (por ejemplo, manejo seguro de localStorage)
```

## Uso de la aplicación

1. Accede a `/login` e inicia sesión con un usuario válido del backend.
2. Una vez autenticado, navega a `/home` para ver todas las listas de reproducción.
3. Utiliza la barra de búsqueda para filtrar listas por nombre en tiempo real.
4. Haz clic en "Ver Detalles" para ver las canciones de una lista en un modal.
5. Haz clic en "Nueva Lista" para crear una lista y añadir canciones dinámicamente.
6. Elimina listas con el botón correspondiente (requiere confirmación).

## Autenticación

- El login se realiza en `/login` y almacena el token JWT en localStorage.
- Todas las rutas protegidas requieren autenticación y el token se envía automáticamente en el header Authorization.
- Si el token expira o es inválido, el usuario es redirigido al login.

### Ejemplo de usuario de prueba

```
{
  "nombreUsuario": "usuario",
  "contrasena": "password123"
}
```

## Endpoints consumidos

- `POST /auth/login` - Iniciar sesión
- `POST /auth/register` - Registrar usuario
- `GET /lists` - Obtener todas las listas
- `GET /lists/{nombre}` - Obtener lista específica
- `POST /lists` - Crear nueva lista
- `DELETE /lists/{nombre}` - Eliminar lista

## Comandos útiles

```bash
ng serve         # Servidor de desarrollo
ng build         # Construir para producción
ng lint          # Linting del código
```

## Configuración

- Modifica `src/environments/environment.ts` para cambiar la URL del backend:
  ```typescript
  export const environment = {
    production: false,
    apiUrl: 'http://localhost:8080' // Cambia según tu configuración
  };
  ```
- Los estilos globales están en `src/styles.scss` y los específicos en cada componente.
