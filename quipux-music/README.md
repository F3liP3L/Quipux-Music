# Quipux Music API

Una API REST para gestión de listas de reproducción de música, implementada con **Arquitectura Limpia (Clean Architecture)** y **Spring Boot**.

## Arquitectura del Proyecto

Este proyecto sigue los principios de **Arquitectura Limpia**, implementando una separación clara de responsabilidades y dependencias:

```
src/main/java/com/quipuxmusic/
├── core/                          # Capa de Dominio y Aplicación
│   ├── domain/                    # Reglas de negocio centrales
│   │   ├── domains/               # Entidades del dominio
│   │   │   ├── User.java
│   │   │   ├── Playlist.java
│   │   │   └── Song.java
│   │   ├── entities/              # Entidades JPA
│   │   │   ├── UserEntity.java
│   │   │   ├── PlaylistEntity.java
│   │   │   └── SongEntity.java
│   │   ├── exception/             # Excepciones customizadas
│   │   ├── port/                  # Interfaces de puertos
│   │   ├── usecase/               # Casos de uso (interfaces)
│   │   └── validator/             # Validadores de dominio
│   └── application/               # Capa de aplicación
│       ├── dto/                   # DTOs de transferencia
│       ├── facade/                # Fachadas de aplicación
│       ├── mapper/                # Mappers para conversión
│       └── usecase/               # Implementaciones de casos de uso
└── infrastructure/                # Capa de infraestructura
    ├── adapter/                   # Adaptadores
    │   ├── primary/               # Adaptadores primarios (entrada)
    │   │   └── controller/        # Controladores REST
    │   └── secondary/             # Adaptadores secundarios (salida)
    │       ├── repository/        # Repositorios JPA
    │       └── service/           # Servicios externos
    ├── configuration/             # Configuración de Spring
    └── init/                      # Inicialización
```

## Características Principales

-  **Arquitectura Limpia**: Separación clara de responsabilidades
- **Autenticación JWT**: Sistema seguro de autenticación
- **Gestión de Playlists**: CRUD completo de listas de reproducción
- **Pruebas Unitarias**: Cobertura completa con JUnit 5 y Mockito
-  **Base de Datos H2**: Base de datos en memoria para desarrollo
- **CORS Configurado**: Soporte para aplicaciones frontend
- **Validaciones**: Validaciones de dominio y entrada
- **Manejo de Errores**: Sistema global de manejo de excepciones

## Tecnologías

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Security + JWT**
- **Spring Data JPA**
- **H2 Database**
- **JUnit 5 + Mockito**
- **Maven**

## Instalación y Ejecución

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/F3liP3L/Quipux-Music.git
   cd quipux-music
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

4. **La aplicación estará disponible en:** `http://localhost:8080`

## Pruebas

### Ejecutar Todas las Pruebas
```bash
mvn test
```

### Ejecutar Pruebas con Reporte de Cobertura
```bash
mvn test jacoco:report
```

### Ejecutar Pruebas Específicas
```bash
# Pruebas de validadores
mvn test -Dtest="com.quipuxmusic.core.domain.validator.*"

# Pruebas de casos de uso
mvn test -Dtest="com.quipuxmusic.core.application.usecase.*"

# Pruebas de controladores
mvn test -Dtest="com.quipuxmusic.infrastructure.adapter.primary.controller.*"
```

## Autenticación

### Endpoints Públicos
Los siguientes endpoints no requieren autenticación:

#### 1. Registrar Usuario
```http
POST /auth/register
Content-Type: application/json

{
    "nombreUsuario": "usuario",
    "contrasena": "password123",
    "rol": "USER"
}
```

#### 2. Iniciar Sesión
```http
POST /auth/login
Content-Type: application/json

{
    "nombreUsuario": "usuario",
    "contrasena": "password123"
}
```

**Respuesta:**
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "nombreUsuario": "usuario",
    "rol": "USER",
    "message": "Login exitoso",
    "status": "EXITO"
}
```

#### 3. Crear Usuarios de Prueba
```http
POST /auth/create-test-users
```

Crea automáticamente:
- **admin** / **admin123** (ADMIN)
- **usuario** / **password123** (USER)
- **test** / **test123** (USER)

### Endpoints Protegidos
Todos los demás endpoints requieren el header de autorización:
```http
Authorization: Bearer <token>
```

## Gestión de Playlists

### 1. Crear Playlist
```http
POST /lists
Authorization: Bearer <token>
Content-Type: application/json

{
    "nombre": "Mi Playlist",
    "descripcion": "Descripción de la playlistDomain",
    "canciones": [
        {
            "titulo": "Bohemian Rhapsody",
            "artista": "Queen",
            "album": "A Night at the Opera",
            "anio": 1975,
            "genero": "Rock"
        }
    ]
}
```

### 2. Obtener Todas las Playlists
```http
GET /lists
Authorization: Bearer <token>
```

### 3. Obtener Playlist por Nombre
```http
GET /lists/{nombre}
Authorization: Bearer <token>
```

**Nota:** Los nombres con espacios o caracteres especiales deben codificarse:
- `Mi Playlist` → `Mi%20Playlist`
- `Rock & Roll` → `Rock%20%26%20Roll`

### 4. Eliminar Playlist
```http
DELETE /lists/{nombre}
Authorization: Bearer <token>
```

## Códigos de Estado HTTP

- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado exitosamente
- `204 No Content` - Operación exitosa sin contenido
- `400 Bad Request` - Datos de entrada inválidos
- `401 Unauthorized` - No autenticado o token inválido
- `404 Not Found` - Recurso no encontrado
- `409 Conflict` - Recurso duplicado
- `500 Internal Server Error` - Error del servidor

## Base de Datos

### H2 Console (Solo Desarrollo)
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuario:** `sa`
- **Contraseña:** `password`

### Configuración JPA
```properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

## Configuración CORS

La API incluye configuración CORS para desarrollo frontend:

**Orígenes permitidos:**
- `http://localhost:*`
- `http://localhost:4200` (Angular)

## Estructura de Pruebas

```
src/test/java/com/quipuxmusic/
├── core/
│   ├── domain/
│   │   ├── validator/
│   │   │   ├── UserValidatorTest.java
│   │   │   └── PlaylistValidatorTest.java
│   │   └── domains/
│   │       └── UserTest.java
│   └── application/
│       ├── usecase/
│       │   ├── CreateUserUseCaseImplTest.java
│       │   └── AuthenticateUserUseCaseImplTest.java
│       └── mapper/
│           ├── UserMapperTest.java
│           ├── PlaylistMapperTest.java
│           └── SongMapperTest.java
└── infrastructure/
    └── adapter/
        └── primary/
            └── controller/
                ├── AuthControllerTest.java
                └── PlaylistControllerTest.java
```

## Configuración de Desarrollo

### Perfil de Desarrollo
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Configuración de Pruebas
El archivo `src/test/resources/application-test.properties` contiene la configuración específica para pruebas.

## Ejemplos de Uso

### Con cURL

#### 1. Autenticación
```bash
# Registrar usuario
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"nombreUsuario":"testuser","contrasena":"password123","rol":"USER"}'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"nombreUsuario":"testuser","contrasena":"password123"}'
```

#### 2. Crear Playlist
```bash
curl -X POST http://localhost:8080/lists \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nombre": "Mi Playlist",
    "descripcion": "Descripción",
    "canciones": [
      {
        "titulo": "Bohemian Rhapsody",
        "artista": "Queen",
        "album": "A Night at the Opera",
        "anio": 1975,
        "genero": "Rock"
      }
    ]
  }'
```

### Con JavaScript
```javascript
// Autenticación
const loginResponse = await fetch('http://localhost:8080/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    nombreUsuario: 'usuario',
    contrasena: 'password123'
  })
});

const { token } = await loginResponse.json();

// Crear playlistDomain
const playlistResponse = await fetch('http://localhost:8080/lists', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    nombre: 'Mi Playlist',
    descripcion: 'Descripción',
    canciones: []
  })
});
```