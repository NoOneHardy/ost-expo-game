# Quick Start Guide

## Prerequisites

- Java 17+
- Docker and Docker Compose
- Gradle 8.5+ (included via wrapper)

## Quick Start with Docker Compose

```bash
# Start all services
docker-compose up -d

# Check logs
docker-compose logs -f

# Stop all services
docker-compose down
```

Access the application:
- Frontend: http://localhost:4200
- Backend API: http://localhost:8080/api/health
- Nginx Proxy: http://localhost

## Development Setup

### Backend (Spring Boot)

```bash
# Build backend
./gradlew :backend:build

# Run backend
./gradlew :backend:bootRun

# Run tests
./gradlew :backend:test
```

Backend will be available at http://localhost:8080
Health check endpoint: http://localhost:8080/api/health

### Frontend (Angular)

```bash
# Install dependencies
./gradlew :frontend:installDependencies

# Build frontend
./gradlew :frontend:buildFrontend

# Serve frontend (development mode)
./gradlew :frontend:serve
```

Or using npm directly:
```bash
cd frontend
npm install
npm start
```

Frontend will be available at http://localhost:4200

### Database (MySQL)

Using Docker:
```bash
docker run -d \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=ost_expo_game \
  --name ost-expo-mysql \
  mysql:8.0
```

Or use the provided MySQL configuration:
```bash
docker build -t ost-expo-mysql ./mysql
docker run -d -p 3306:3306 --name ost-expo-mysql ost-expo-mysql
```

## Gradle Commands

### Root Project

```bash
# Build all modules
./gradlew build

# Clean all modules
./gradlew clean

# List all projects
./gradlew projects

# List all available tasks
./gradlew tasks
```

### Individual Modules

```bash
# Backend
./gradlew :backend:build
./gradlew :backend:bootRun
./gradlew :backend:test

# Frontend
./gradlew :frontend:build
./gradlew :frontend:serve
./gradlew :frontend:testFrontend

# Proxy
./gradlew :proxy:build

# MySQL
./gradlew :mysql:build
```

## Project Structure

```
ost-expo-game/
├── backend/              # Spring Boot backend
│   ├── src/
│   │   ├── main/java/   # Application code
│   │   └── test/java/   # Test code
│   ├── Dockerfile
│   └── build.gradle
├── frontend/            # Angular frontend
│   ├── src/
│   │   └── app/         # Angular components
│   ├── Dockerfile
│   ├── nginx-frontend.conf
│   └── build.gradle
├── proxy/               # Nginx reverse proxy
│   ├── nginx.conf
│   ├── Dockerfile
│   └── build.gradle
├── mysql/               # MySQL database
│   ├── init.sql
│   ├── Dockerfile
│   └── build.gradle
├── build.gradle         # Root Gradle config
├── settings.gradle      # Multi-module settings
└── docker-compose.yml   # Docker orchestration
```

## Troubleshooting

### Backend won't start
- Check if MySQL is running: `docker ps | grep mysql`
- Verify database credentials in `application.properties`
- Check logs: `./gradlew :backend:bootRun --info`

### Frontend build fails
- Clear node_modules: `rm -rf frontend/node_modules`
- Clear Gradle cache: `./gradlew :frontend:clean`
- Reinstall: `./gradlew :frontend:installDependencies`

### Docker Compose issues
- Check container logs: `docker-compose logs [service-name]`
- Restart services: `docker-compose restart`
- Clean restart: `docker-compose down && docker-compose up -d`

## API Endpoints

### Backend REST API

- `GET /api/health` - Health check endpoint
  ```json
  {
    "status": "UP",
    "service": "OST Expo Game Backend"
  }
  ```

## Database Schema

Tables created by `mysql/init.sql`:

- `game_sessions` - Game session data
- `high_scores` - High score tracking

## Next Steps

1. Add your business logic to the backend controllers
2. Create Angular components for your UI
3. Update MySQL schema as needed
4. Configure Nginx proxy rules if needed
5. Add integration tests
6. Set up CI/CD pipeline

## Security Summary

✅ CodeQL analysis passed with 0 vulnerabilities
- Java: No alerts found
- JavaScript: No alerts found

Note: Frontend npm dependencies show some vulnerabilities in dev dependencies. Run `npm audit` for details.
