# Quipux Music Frontend

Aplicación Angular para gestionar listas de reproducción musical, construida con Angular 20 y Angular Material.

## 🚀 Características

- **Autenticación JWT**: Sistema de login seguro con tokens
- **Gestión de Listas**: Crear, ver, buscar y eliminar listas de reproducción
- **Interfaz Moderna**: Diseño responsive con Angular Material
- **Búsqueda en Tiempo Real**: Buscar listas por nombre
- **Modales Interactivos**: Ver detalles de listas y crear nuevas
- **Validación de Formularios**: Validación completa en todos los formularios

## 🛠️ Tecnologías

- **Angular 20**: Framework principal
- **Angular Material**: Componentes UI
- **TypeScript**: Tipado fuerte
- **RxJS**: Programación reactiva
- **SCSS**: Estilos avanzados
- **JWT**: Autenticación

## 📋 Requisitos Previos

- Node.js (versión 18 o superior)
- npm o yarn
- Backend Quipux Music API ejecutándose en `http://localhost:8080`

## 🔧 Instalación

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd quipux-frontend
```

2. **Instalar dependencias**
```bash
npm install
```

3. **Configurar el backend**
Asegúrate de que el backend esté ejecutándose en `http://localhost:8080` o modifica la URL en `src/environments/environment.ts`

4. **Ejecutar la aplicación**
```bash
ng serve
```

5. **Abrir en el navegador**
Navega a `http://localhost:4200`

## 🎯 Funcionalidades

### Autenticación
- **Login**: `/login` - Formulario de autenticación
- **Guard de Rutas**: Protección automática de rutas
- **Persistencia de Token**: Almacenamiento en localStorage

### Gestión de Listas
- **Ver Todas**: Muestra todas las listas de reproducción
- **Buscar**: Búsqueda en tiempo real por nombre
- **Ver Detalles**: Modal con información completa de canciones
- **Crear Nueva**: Formulario dinámico para agregar canciones
- **Eliminar**: Confirmación antes de eliminar

### Interfaz de Usuario
- **Responsive**: Adaptable a diferentes tamaños de pantalla
- **Material Design**: Componentes modernos y accesibles
- **Estados de Carga**: Indicadores visuales durante operaciones
- **Mensajes de Error**: Notificaciones claras para el usuario

## 📁 Estructura del Proyecto

```
src/
├── app/                    # Configuración principal
├── components/            # Componentes de la aplicación
│   ├── home/             # Página principal con listas
│   └── login/            # Formulario de autenticación
├── interfaces/           # Interfaces TypeScript
│   ├── auth.interface.ts
│   └── playlist.interface.ts
├── service/              # Servicios de datos
│   ├── auth.service.ts   # Autenticación
│   └── listService.ts    # Gestión de listas
├── router/               # Configuración de rutas
│   └── guards/           # Guards de autenticación
├── interceptor/          # Interceptores HTTP
└── environments/         # Configuración de entorno
```

## 🔐 Autenticación

### Credenciales de Prueba
El backend incluye usuarios de prueba que puedes usar:

```json
{
  "nombreUsuario": "usuario",
  "contrasena": "password123"
}
```

### Flujo de Autenticación
1. Usuario accede a `/login`
2. Ingresa credenciales
3. Sistema valida con backend
4. Token JWT se almacena automáticamente
5. Usuario es redirigido a `/home`
6. Todas las peticiones incluyen el token automáticamente

## 🎵 API Endpoints

La aplicación consume los siguientes endpoints:

- `POST /auth/login` - Iniciar sesión
- `POST /auth/register` - Registrar usuario
- `GET /lists` - Obtener todas las listas
- `GET /lists/{nombre}` - Obtener lista específica
- `POST /lists` - Crear nueva lista
- `DELETE /lists/{nombre}` - Eliminar lista

## 🚀 Comandos Útiles

```bash
# Servidor de desarrollo
ng serve

# Construir para producción
ng build

# Ejecutar tests
ng test

# Linting
ng lint
```

## 🔧 Configuración

### Variables de Entorno
Modifica `src/environments/environment.ts` para cambiar la URL del backend:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080' // Cambiar según tu configuración
};
```

### Estilos
Los estilos están en SCSS y siguen la metodología BEM. Los archivos principales:
- `src/styles.scss` - Estilos globales
- `src/components/*/*.scss` - Estilos específicos de componentes

## 🐛 Solución de Problemas

### Error de CORS
Si encuentras errores de CORS, asegúrate de que el backend esté configurado correctamente.

### Token Expirado
Si el token expira, el usuario será redirigido automáticamente al login.

### Problemas de Conexión
Verifica que el backend esté ejecutándose en la URL correcta.

## 📝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 🤝 Soporte

Para soporte técnico o preguntas, contacta al equipo de desarrollo.
