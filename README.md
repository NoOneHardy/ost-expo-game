# OST Expo Game

The software for the expo game for the OST developed during a hack- and makeathon.

## Project Structure

This is a multi-module Gradle project with the following components:

```
ost-expo-game/
├── backend/          # Spring Boot application
├── frontend/         # Angular application
├── proxy/            # Nginx reverse proxy
├── mysql/            # MySQL database configuration
├── build.gradle      # Root Gradle configuration
├── settings.gradle   # Gradle multi-module settings
└── docker-compose.yml # Docker orchestration
```

### Backend (Spring Boot)

- **Location**: `backend/`
- **Technology**: Spring Boot 3.2.2 with Java 17
- **Port**: 8080
- **Features**:
  - RESTful API
  - Spring Data JPA for database access
  - MySQL integration

### Frontend (Angular)

- **Location**: `frontend/`
- **Technology**: Angular 19.2.18
- **Port**: 4200
- **Features**:
  - Modern Angular standalone components
  - Routing configured
  - CSS styling
  - Security patches applied (XSRF & XSS vulnerabilities fixed)

### Proxy (Nginx)

- **Location**: `proxy/`
- **Technology**: Nginx
- **Port**: 80
- **Features**:
  - Reverse proxy for frontend and backend
  - Routes `/api/` requests to backend
  - Serves frontend on root path

### MySQL Database

- **Location**: `mysql/`
- **Technology**: MySQL 8.0
- **Port**: 3306
- **Features**:
  - Pre-configured with initial schema
  - Database: `ost_expo_game`
  - Tables for game sessions and high scores

## Prerequisites

- Java 17 or higher
- Node.js 20.11.1 or higher (for frontend development)
- Docker and Docker Compose (for containerized deployment)
- Gradle 8.5 or higher (wrapper included)

## Configuration

This project uses a **simplified, centralized configuration** approach:

- **Version management**: All versions (Java, Spring Boot, Node.js) are defined in `gradle.properties`
- **Environment profiles**: Spring Boot uses profiles (`default` for local, `docker` for containers)
- **Environment variables**: Docker Compose supports `.env` file for custom database credentials
- **Shared Gradle config**: Common repository and Java settings in root `build.gradle`

See [QUICKSTART.md](QUICKSTART.md#configuration-management) for detailed configuration documentation.

## Building the Project

### Using Gradle

Build all modules:
```bash
./gradlew build
```

Build specific module:
```bash
./gradlew :backend:build
./gradlew :frontend:build
```

Clean the project:
```bash
./gradlew clean
```

### Backend Only

```bash
cd backend
../gradlew bootJar
```

### Frontend Only

```bash
cd frontend
npm install
npm run build
```

## Running the Application

### Using Docker Compose (Recommended)

Start all services:
```bash
docker-compose up -d
```

Stop all services:
```bash
docker-compose down
```

View logs:
```bash
docker-compose logs -f
```

### Running Services Individually

#### Backend
```bash
cd backend
../gradlew bootRun
```

#### Frontend
```bash
cd frontend
npm start
```

#### MySQL
```bash
docker run -d \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=ost_expo_game \
  --name ost-expo-mysql \
  mysql:8.0
```

## Accessing the Application

- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080
- **Proxy**: http://localhost (when using Docker Compose)
- **MySQL**: localhost:3306

## Development

### Backend Development

The backend uses Spring Boot with the following configuration:
- Database URL: `jdbc:mysql://localhost:3306/ost_expo_game`
- Username: `root`
- Password: `root`

### Frontend Development

The frontend uses Angular CLI. Common commands:
- `npm start` - Start development server
- `npm test` - Run unit tests
- `npm run build` - Build for production

## Testing

Run all tests:
```bash
./gradlew test
```

Run backend tests:
```bash
./gradlew :backend:test
```

Run frontend tests:
```bash
cd frontend
npm test
```

## Project Configuration

### Gradle Modules

All modules are configured in `settings.gradle`:
- `frontend` - Angular application with Node.js Gradle plugin
- `backend` - Spring Boot application
- `proxy` - Nginx configuration
- `mysql` - MySQL configuration

### Environment Variables

Backend environment variables (can be set in `docker-compose.yml` or `application.properties`):
- `SPRING_DATASOURCE_URL` - MySQL connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password

## License

See LICENSE file for details.
