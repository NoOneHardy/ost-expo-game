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

### Custom Environment Variables (Optional)

To customize database settings, create a `.env` file in the root directory:

```bash
# Copy the example file
cp .env.example .env

# Edit with your values
DB_ROOT_PASSWORD=your_password
DB_NAME=your_database_name
```

The `.env` file is git-ignored and will override the default values in `docker-compose.yml`.

## Development Setup

### Backend (Spring Boot)

```bash
# Build backend
./gradlew :backend:build

# Run backend (local development with localhost MySQL)
./gradlew :backend:bootRun

# Run backend with Docker profile (connects to mysql container)
./gradlew :backend:bootRun --args='--spring.profiles.active=docker'

# Run tests
./gradlew :backend:test
```

Backend will be available at http://localhost:8080
Health check endpoint: http://localhost:8080/api/health

**Configuration Profiles:**
- **default**: Uses `localhost:3306` for database connection (local development)
- **docker**: Uses `mysql:3306` for database connection (Docker environments)

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
│   │   ├── main/
│   │   │   ├── java/   # Application code
│   │   │   └── resources/
│   │   │       ├── application.properties        # Default config (localhost)
│   │   │       └── application-docker.properties # Docker profile config
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
├── build.gradle         # Root Gradle config with shared settings
├── gradle.properties    # Centralized version configuration
├── settings.gradle      # Multi-module settings
├── .env.example         # Example environment variables
└── docker-compose.yml   # Docker orchestration
```

## Configuration Management

The project uses a simplified, centralized configuration approach:

### Gradle Configuration
- **`gradle.properties`**: Central location for version numbers (Java, Spring Boot, Node.js, npm)
- **`build.gradle` (root)**: Shared repository and Java configuration for all submodules
- **Submodule `build.gradle` files**: Minimal, focused on module-specific dependencies

### Application Configuration
- **`application.properties`**: Default configuration for local development (localhost database)
- **`application-docker.properties`**: Docker-specific overrides (mysql container hostname)
- Spring profiles automatically activated via `SPRING_PROFILES_ACTIVE` environment variable

### Docker Configuration
- **`docker-compose.yml`**: Uses environment variable substitution with sensible defaults
- **`.env` file** (optional, git-ignored): Override defaults without modifying docker-compose.yml
- Database credentials can be customized via environment variables

## Troubleshooting

### Backend won't start
- Check if MySQL is running: `docker ps | grep mysql`
- Verify the correct profile is active:
  - Local development: Uses default profile (localhost:3306)
  - Docker: Uses docker profile (mysql:3306) - activated automatically by docker-compose
- Check database credentials match between `.env` (if used) and `application*.properties`
- Check logs: `./gradlew :backend:bootRun --info`

### Frontend build fails
- Clear node_modules: `rm -rf frontend/node_modules`
- Clear Gradle cache: `./gradlew :frontend:clean`
- Reinstall: `./gradlew :frontend:installDependencies`

### Docker Compose issues
- Check container logs: `docker-compose logs [service-name]`
- Verify environment variables: Check `.env` file if you created one
- Restart services: `docker-compose restart`
- Clean restart: `docker-compose down && docker-compose up -d`

### Configuration changes not taking effect
- For Gradle: Run `./gradlew clean` and rebuild
- For Docker: Run `docker-compose down` and `docker-compose up --build -d`
- For Spring profiles: Ensure `SPRING_PROFILES_ACTIVE` is set correctly

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

✅ Angular security vulnerabilities resolved:
- Upgraded from Angular 17.3.12 to 19.2.18
- Fixed XSRF Token Leakage vulnerability
- Fixed XSS via Unsanitized SVG Script Attributes
- Fixed Stored XSS via SVG Animation, SVG URL and MathML Attributes
- All production dependencies are now secure (0 vulnerabilities)

Note: Some dev dependencies have known vulnerabilities in non-production code paths.
