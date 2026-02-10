# Bank App Challenge

Esta solución implementa una arquitectura de Microservicios (simulada en Spring Boot modular) con Backend en Java Spring Boot y Frontend en React.

## Requisitos Previos

- Docker y Docker Compose
- Java 17 (Opcional si se usa Docker)
- Node.js 18+ (Opcional si se usa Docker)

## Estructura del Proyecto

- `backend/`: Código fuente Java Spring Boot.
- `frontend/`: Código fuente React (Vite).
- `BaseDatos.sql`: Script de inicialización de Base de Datos.
- `docker-compose.yml`: Orquestación de contenedores.

## Instrucciones de Despliegue (Docker)

1. Abrir una terminal en la raíz del proyecto `bank-app`.
2. Ejecutar el comando:
   ```bash
   docker-compose up --build
   ```
   Esto levantará:
   - **Base de Datos (MySQL)** en puerto `3306`.
   - **Backend (Spring Boot)** en puerto `8080`.

3. **Frontend**: El Frontend no está incluido en el `docker-compose.yml` por simplicidad de desarrollo local, pero tiene un `Dockerfile`. Para correrlo con Docker:
   ```bash
   cd frontend
   docker build -t bank-frontend .
   docker run -p 5173:5173 bank-frontend
   ```
   Acceder a `http://localhost:5173`.

## Instrucciones de Ejecución Local (Sin Docker)

### Backend
1. Ir a la carpeta `backend`.
2. Ejecutar:
   ```bash
   ./gradlew bootRun
   ```
   *Nota*: Asegúrese de tener una base de datos MySQL corriendo o configure `application.properties` para usar H2 (por defecto está configurado para H2 si no se detectan variables de entorno de Docker, pero el `docker-compose` inyecta las variables para MySQL).

### Frontend
1. Ir a la carpeta `frontend`.
2. Instalar dependencias:
   ```bash
   npm install
   ```
3. Ejecutar:
   ```bash
   npm start
   ```
   Acceder a `http://localhost:4200`.

## Testing

### Backend
Ejecutar tests unitarios:
```bash
cd backend
./gradlew test
```

### Frontend
Ejecutar tests (Jest):
```bash
cd frontend
npm test
```

## Postman
Se incluye el archivo `BankApp.postman_collection.json` para importar en Postman y probar los endpoints.
