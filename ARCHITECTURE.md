# OST Expo Game - Architecture Diagram

## System Overview

This document describes the architecture of the OST Expo Game, a racing game application developed for the OST expo.

## Architecture Diagram

```mermaid
graph TB
    subgraph "Client Layer"
        Client[Web Browser]
    end

    subgraph "Proxy Layer - Port 26200"
        Proxy[Nginx Proxy<br/>Port 80]
    end

    subgraph "Frontend Layer"
        Frontend[Angular Frontend<br/>TypeScript/Angular 21]
        FrontendComponents[Components:<br/>- Scoreboard View<br/>- Race View<br/>- Services & State Management]
    end

    subgraph "Backend Layer - Port 26201"
        Backend[Spring Boot Backend<br/>Java 21]
        
        subgraph "Presentation Layer"
            REST[REST Controllers]
            WS[WebSocket/STOMP<br/>Real-time Updates]
        end
        
        subgraph "Application Layer"
            RaceService[Race Services:<br/>- RaceStartService<br/>- RaceEndService]
            ScoreboardService[Scoreboard Services:<br/>- LoadScoreboardService<br/>- BuildScoreboardService<br/>- CreateSnapshotService]
            CommonService[Common Services:<br/>- ViewModeService]
        end
        
        subgraph "Domain Layer"
            RaceDomain[Race Domain<br/>- Race Model<br/>- Race Events]
            ScoreboardDomain[Scoreboard Domain<br/>- Scoreboard Model]
        end
        
        subgraph "Infrastructure Layer"
            JPA[JPA Persistence<br/>MySQL Access]
            Mongo[MongoDB Persistence<br/>Snapshot Storage]
            Kurrent[KurrentDB Client<br/>Event Streaming]
            EventBus[Event Bus & Listeners]
        end
    end

    subgraph "Data Layer"
        MySQL[(MySQL Database<br/>Port 26206<br/>- Race Data<br/>- Race Results)]
        MongoDB[(MongoDB<br/>Port 26207<br/>- Scoreboard Snapshots<br/>- Score Documents)]
        KurrentDB[(KurrentDB<br/>Port 26208<br/>- Event Store<br/>- Score Events)]
    end

    %% Client to Proxy
    Client -->|HTTP/HTTPS| Proxy

    %% Proxy routing
    Proxy -->|/api/*| Backend
    Proxy -->|/ws| WS
    Proxy -->|/* (other)| Frontend

    %% Frontend to Backend
    Frontend -->|REST API| REST
    Frontend -->|WebSocket| WS

    %% Frontend components
    Frontend --> FrontendComponents

    %% Backend internal flow
    REST --> RaceService
    REST --> ScoreboardService
    REST --> CommonService
    
    WS --> EventBus
    
    RaceService --> RaceDomain
    ScoreboardService --> ScoreboardDomain
    
    RaceService --> JPA
    RaceService --> Kurrent
    
    ScoreboardService --> Mongo
    ScoreboardService --> Kurrent
    
    EventBus --> RaceService
    EventBus --> ScoreboardService

    %% Backend to Databases
    JPA -->|JDBC| MySQL
    Mongo -->|MongoDB Protocol| MongoDB
    Kurrent -->|gRPC| KurrentDB

    %% Event flow
    KurrentDB -.->|Event Stream| Kurrent
    EventBus -.->|Real-time Updates| WS

    style Client fill:#e1f5ff
    style Proxy fill:#fff4e1
    style Frontend fill:#e8f5e9
    style Backend fill:#f3e5f5
    style MySQL fill:#ffebee
    style MongoDB fill:#fff3e0
    style KurrentDB fill:#e0f2f1
```

## Component Details

### 1. Client Layer
- **Web Browser**: Users access the application through modern web browsers
- Receives the Angular SPA and communicates via HTTP/WebSocket

### 2. Proxy Layer (Nginx)
- **Port**: 26200 (external), 80 (internal)
- **Purpose**: Reverse proxy and routing
- **Routes**:
  - `/api/*` → Backend REST API (removes `/api` prefix)
  - `/ws` → Backend WebSocket endpoint
  - `/*` (all other) → Frontend Angular application
- **Features**: WebSocket upgrade support for real-time communication

### 3. Frontend Layer (Angular)
- **Technology**: Angular 21, TypeScript, RxJS
- **State Management**: NgRx Store
- **Key Features**:
  - Real-time scoreboard updates via WebSocket/STOMP
  - Race management interface
  - View mode switching
- **Key Services**:
  - ScoreboardService: Manages scoreboard data and updates
  - ViewModeService: Handles view mode state

### 4. Backend Layer (Spring Boot)
- **Technology**: Java 21, Spring Boot 4.0.2
- **Port**: 26201
- **Architecture Pattern**: Hexagonal/Clean Architecture

#### Presentation Layer
- **REST Controllers**: Handle HTTP requests for races and scoreboard
- **WebSocket/STOMP**: Provides real-time updates to frontend clients

#### Application Layer
- **Race Services**: Handle race lifecycle (start, end)
- **Scoreboard Services**: Manage scoreboard state, snapshots, and queries
- **Common Services**: Shared services like view mode management

#### Domain Layer
- **Race Domain**: Core race business logic and models
- **Scoreboard Domain**: Scoreboard aggregation and business rules

#### Infrastructure Layer
- **JPA Persistence**: MySQL database access for race data
- **MongoDB Persistence**: Snapshot storage and document-based queries
- **KurrentDB Client**: Event sourcing and event streaming
- **Event Bus**: Application event handling and dispatch

### 5. Data Layer

#### MySQL Database
- **Port**: 26206
- **Version**: 9.5.0
- **Purpose**: Primary relational data store
- **Data**: 
  - Race entities and metadata
  - Race results and timings

#### MongoDB
- **Port**: 26207
- **Version**: 8.2.5
- **Purpose**: Document store for snapshots
- **Data**:
  - Scoreboard snapshots
  - Score documents for fast reads

#### KurrentDB (Event Store)
- **Port**: 26208
- **Version**: 25.1
- **Purpose**: Event sourcing and streaming
- **Data**:
  - Score received events
  - Event streams for audit and replay
- **Features**:
  - Projections enabled
  - AtomPub over HTTP
  - Insecure mode for development

## Data Flow

### Race Start Flow
1. Client initiates race start via REST API
2. Backend processes request through RaceStartService
3. Race entity stored in MySQL via JPA
4. Race start event published to event bus
5. Real-time notification sent to clients via WebSocket

### Race End Flow
1. Client submits race results via REST API
2. Backend processes through RaceEndService
3. Race data updated in MySQL
4. Score event published to KurrentDB
5. Event listeners trigger scoreboard rebuild
6. Updated scoreboard sent to clients via WebSocket

### Scoreboard Update Flow
1. New scores arrive in KurrentDB event stream
2. Backend reads new events via KurrentDB client
3. Scoreboard service builds/updates scoreboard
4. Snapshot stored in MongoDB for fast retrieval
5. Real-time update pushed to clients via WebSocket/STOMP

## Technology Stack

### Frontend
- **Framework**: Angular 21
- **Language**: TypeScript 5.9
- **State**: NgRx Store 21
- **Real-time**: STOMP.js 7.3
- **HTTP**: RxJS 7.8

### Backend
- **Framework**: Spring Boot 4.0.2
- **Language**: Java 21
- **Persistence**: 
  - Spring Data JPA (MySQL)
  - Spring Data MongoDB
  - KurrentDB Client 1.1.1
- **Mapping**: MapStruct 1.7
- **Build**: Gradle 8.x

### Infrastructure
- **Databases**:
  - MySQL 9.5.0
  - MongoDB 8.2.5
  - KurrentDB 25.1
- **Proxy**: Nginx (latest)
- **Container**: Docker & Docker Compose

## Deployment

The application is containerized using Docker and orchestrated with Docker Compose:
- All services defined in `docker-compose.yml`
- Environment-specific configuration via `.env` files
- Named volumes for data persistence
- Internal Docker network for service communication

## Key Design Patterns

1. **Hexagonal Architecture**: Clear separation of concerns with ports and adapters
2. **Event Sourcing**: KurrentDB stores all score events for audit and replay
3. **CQRS**: Separate models for commands (MySQL) and queries (MongoDB snapshots)
4. **Real-time Updates**: WebSocket/STOMP for live scoreboard updates
5. **API Gateway Pattern**: Nginx proxy as single entry point
