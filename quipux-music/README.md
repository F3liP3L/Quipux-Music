# API de Listas de Reproducción

Esta es una API REST para gestionar listas de reproducción de música, implementada con Spring Boot, JPA, H2 y autenticación JWT.

## Características

- ✅ Autenticación y autorización con JWT
- ✅ Gestión de listas de reproducción (CRUD)
- ✅ Base de datos H2 en memoria
- ✅ Validaciones de entrada
- ✅ Manejo global de errores
- ✅ Arquitectura limpia

## Tecnologías

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Security + JWT**
- **Spring Data JPA**
- **H2 Database**
- **Maven**

## Endpoints de la API

### Autenticación

**Nota:** Los endpoints de autenticación son públicos y no requieren token previo.

### 🔒 Configuración de Seguridad

La aplicación está configurada con Spring Security y JWT:

- **Rutas públicas:** `/auth/**` (registro, login, crear usuarios de prueba)
- **Rutas protegidas:** Todas las demás rutas requieren token JWT válido
- **Consola H2:** `/h2-console/**` (accesible para desarrollo)

### 🌐 Configuración CORS

La aplicación incluye configuración CORS para permitir acceso desde aplicaciones frontend:

**Orígenes permitidos:**
- `http://localhost:*` - Desarrollo local
- `http://localhost:3000` - React default
- `http://localhost:4200` - Angular default
- `http://localhost:8081` - Vue default
- `http://localhost:5173` - Vite default
- `https://*.vercel.app` - Vercel deployments
- `https://*.netlify.app` - Netlify deployments
- `https://*.github.io` - GitHub Pages
- `https://*.herokuapp.com` - Heroku deployments

**Configuración para desarrollo:**
```bash
# Activar perfil de desarrollo (CORS más permisivo)
mvn spring-boot:run -Dspring.profiles.active=dev
```

**Códigos de estado HTTP:**
- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado exitosamente
- `401 Unauthorized` - Token inválido o ausente
- `403 Forbidden` - Acceso denegado
- `404 Not Found` - Recurso no encontrado

#### 1. Registrar Usuario
```
POST /auth/register
Content-Type: application/json

{
    "username": "usuario",
    "password": "password123",
    "role": "USER"
}
```

#### 2. Iniciar Sesión
```
POST /auth/login
Content-Type: application/json

{
    "username": "usuario",
    "password": "password123"
}
```

**Respuesta:**
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

### Listas de Reproducción

**Nota:** Todos los endpoints de listas requieren autenticación. Incluir el token en el header:
```
Authorization: Bearer <token>
```

#### 1. Crear Lista de Reproducción
```
POST /lists
Content-Type: application/json
Authorization: Bearer <token>

{
    "name": "Mi Lista Favorita",
    "description": "Canciones que me gustan",
    "songEntities": [
        {
            "title": "Bohemian Rhapsody",
            "artist": "Queen",
            "album": "A Night at the Opera",
            "year": "1975",
            "genre": "Rock"
        }
    ]
}
```

**Respuesta:** `201 Created` con la lista creada

#### 2. Obtener Todas las Listas
```
GET /lists
Authorization: Bearer <token>
```

**Respuesta:** `200 OK` con array de listas

#### 3. Obtener Lista por Nombre
```
GET /lists/{listName}
Authorization: Bearer <token>
```

**Nota:** Si el nombre contiene espacios o caracteres especiales, debe codificarse en la URL:
- `Pop Latino` → `Pop%20Latino`
- `Rock & Roll` → `Rock%20%26%20Roll`

**Respuesta:** `200 OK` con la lista o `404 Not Found`

#### 4. Eliminar Lista
```
DELETE /lists/{listName}
Authorization: Bearer <token>
```

**Nota:** Aplican las mismas reglas de codificación que para GET.

**Respuesta:** `204 No Content` o `404 Not Found`

## Códigos de Estado HTTP

- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado exitosamente
- `204 No Content` - Operación exitosa sin contenido
- `400 Bad Request` - Datos de entrada inválidos
- `401 Unauthorized` - No autenticado
- `404 Not Found` - Recurso no encontrado
- `500 Internal Server Error` - Error del servidor

## Ejecutar la Aplicación

1. **Clonar el repositorio**
2. **Ejecutar con Maven:**
   ```bash
   mvn spring-boot:run
   ```
3. **La aplicación estará disponible en:** `http://localhost:8080`

## Scripts de Prueba

### Test de Arranque
```bash
./test-startup.sh
```
Verifica que la aplicación arranca sin errores de dependencias cíclicas.

### Test de Tablas
```bash
./test-tables.sh
```
Verifica que las tablas se crean correctamente al arrancar.

### Test de Dependencias
```bash
./test-dependencies.sh
```
Verifica que todas las dependencias están correctamente configuradas.

### Test de Seguridad
```bash
./test-security.sh
```
Verifica que las rutas están correctamente protegidas y que la autenticación JWT funciona.

### Test de CORS
```bash
./test-cors.sh
```
Verifica que la configuración CORS funciona correctamente para aplicaciones frontend.

## Usuarios de Prueba

Para crear usuarios de prueba, ejecutar:
```bash
curl -X POST http://localhost:8080/auth/create-test-users
```

Esto creará:
- **Usuario:** `admin` / **Contraseña:** `admin123` / **Rol:** `ADMIN`
- **Usuario:** `usuario` / **Contraseña:** `password123` / **Rol:** `USER`
- **Usuario:** `test` / **Contraseña:** `test123` / **Rol:** `USER`

## Base de Datos H2

- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuario:** `sa`
- **Contraseña:** `password`

## Configuración JPA

La aplicación está configurada para crear automáticamente las tablas al arrancar:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

Esto significa que:
- **create-drop**: Las tablas se crean al iniciar y se eliminan al cerrar
- Las tablas se crean automáticamente basándose en las entidades JPA
- No es necesario crear manualmente las tablas en la base de datos

### ⚠️ Nota Importante sobre Nombres de Columnas

En la entidad `Song`, la columna `year` se mapea como `release_year` en la base de datos porque `year` es una palabra reservada en H2:

```java
@Column(name = "release_year", nullable = false)
private String year;
```

Esto evita errores de sintaxis SQL al crear las tablas.

## Ejemplo de Uso con Postman

### 🔓 Paso 1: Autenticación (Ruta Pública)
```
POST http://localhost:8080/auth/login
Content-Type: application/json

Body:
{
    "username": "usuario",
    "password": "password123"
}

Respuesta:
{
    "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

### 🔒 Paso 2: Crear Lista (Ruta Protegida)
```
POST http://localhost:8080/lists
Content-Type: application/json
Authorization: Bearer <token_del_paso_anterior>

Body:
{
    "name": "Rock Clásico",
    "description": "Los mejores del rock",
    "songEntities": [
        {
            "title": "Stairway to Heaven",
            "artist": "Led Zeppelin",
            "album": "Led Zeppelin IV",
            "year": "1971",
            "genre": "Rock"
        }
    ]
}
```

### 🔒 Paso 3: Obtener Listas (Ruta Protegida)
```
GET http://localhost:8080/lists
Authorization: Bearer <token>
```

### ⚠️ Nota Importante
- **Sin token:** Las rutas protegidas devuelven `401 Unauthorized`
- **Token inválido:** Las rutas protegidas devuelven `401 Unauthorized`
- **Token válido:** Las rutas protegidas funcionan normalmente

## 🌐 Uso desde Frontend

### Ejemplo con JavaScript
```javascript
// Incluir el archivo examples/frontend-example.js
const api = new PlaylistAPI();

// Autenticación
await api.login('usuario', 'password123');

// Crear lista
const lista = await api.createPlaylist({
    name: 'Mi Lista',
    description: 'Descripción',
    songEntities: [...]
});
```

### Headers CORS Incluidos
La API incluye automáticamente los headers CORS necesarios:
- `Access-Control-Allow-Origin`
- `Access-Control-Allow-Methods`
- `Access-Control-Allow-Headers`
- `Access-Control-Allow-Credentials`

## Estructura del Proyecto

```
src/main/java/com/quipuxmovie/
├── core/
│   ├── domain/
│   │   ├── entities/          # Entidades JPA
│   │   └── dto/              # DTOs
│   └── application/
│       ├── service/          # Lógica de negocio
│       └── dto/              # DTOs de aplicación
└── infrastructure/
    ├── adapter/
    │   ├── primary/
    │   │   └── controller/   # Controladores REST
    │   └── secondary/
    │       └── repository/   # Repositorios JPA
    ├── configuration/        # Configuración de seguridad
    └── init/                # Inicialización de datos
```

## Validaciones

- El nombre de la lista no puede ser nulo o vacío
- No pueden existir dos listas con el mismo nombre
- Todos los campos de canción son requeridos
- El nombre de usuario debe ser único 