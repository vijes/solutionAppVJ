# Bank App Challenge

Esta solución implementa una aplicación bancaria con Backend en **Java Spring Boot 3** y Frontend en **Angular 17**.

## Requisitos Previos

- Docker y Docker Compose (Recomendado para MySQL)
- Java 17+
- Node.js 18+ (Angular CLI opcional)

## Estructura del Proyecto

- `backend/`: API REST Java Spring Boot (Gradle).
- `frontend/`: Aplicación Angular 17.
- `BaseDatos.sql`: Script de inicialización de SQL.
- `docker-compose.yml`: Orquestación para MySQL y Backend.
- `Technical_Report.md`: Informe técnico detallado de la arquitectura.

## Instrucciones de Ejecución Local (Windows)

### 1. Base de Datos
Tienes dos opciones para la base de datos:
- **H2 (Memoria)**: No requiere configuración. El backend la usa por defecto si no se especifica perfil.
- **MySQL (Docker)**: Recomendado.
  ```powershell
  docker-compose up bank-db -d
  ```

### 2. Backend
Desde la carpeta `backend`:
- **Opción H2**: `.\gradlew bootRun`
- **Opción MySQL**: `.\gradlew bootRun --args='--spring.profiles.active=local'`

### 3. Frontend
Desde la carpeta `frontend`:
1. Instalar dependencias: `npm install`
2. Ejecutar: `npm start`
3. Acceder a: `http://localhost:4200`

## Características Principales del Frontend
- **Layout de Dashboard**: Header superior y Sidebar lateral persistente.
- **Rutas Anidadas**: La aplicación principal reside en `/home`.
- **Modales UI**: Formularios de creación/edición integrados mediante ventanas modales.
- **Indicador de Procesamiento**: Bloqueo de pantalla y spinner durante llamadas al API.
- **Feedback Visual**: Badges de colores para el estado de clientes (Activo/Inactivo).

## Testing
- **Backend**: `.\gradlew test`
- **Frontend**: `npm test` (Unit tests con Jest)

---
*Para más detalles técnicos, consulte el archivo [Technical_Report.md](./Technical_Report.md)*
