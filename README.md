# ost-expo-game

The software for the expo game for the OST developed during a hack- and makeathon.

## Overview

This is a Node.js/Express backend API for managing racing games. The API provides endpoints to create and manage races.

## Features

- Create new races with optional participants
- Retrieve race information by ID
- List all races
- RESTful API design
- TypeScript implementation
- Comprehensive test coverage

## Prerequisites

- Node.js (v14 or higher)
- npm (v6 or higher)

## Installation

```bash
npm install
```

## Running the Application

### Development Mode

```bash
npm run dev
```

The server will start on port 3000 (or the port specified in the `PORT` environment variable).

### Production Mode

```bash
npm run build
npm start
```

## API Endpoints

### Health Check

**GET** `/health`

Returns the server status.

**Response:**
```json
{
  "status": "ok",
  "timestamp": "2026-02-13T16:18:07.512Z"
}
```

### Create a New Race

**POST** `/api/race/start`

Creates a new race and returns the race details.

**Request Body (optional):**
```json
{
  "participants": ["Alice", "Bob", "Charlie"]
}
```

**Response (201 Created):**
```json
{
  "id": "4203e29f-06c3-442b-897a-a5ca4469acfd",
  "startTime": "2026-02-13T16:18:02.797Z",
  "status": "pending",
  "participants": ["Alice", "Bob", "Charlie"],
  "createdAt": "2026-02-13T16:18:02.797Z"
}
```

**Error Response (400 Bad Request):**
```json
{
  "error": "Invalid request",
  "message": "participants must be an array"
}
```

### Get Race by ID

**GET** `/api/race/:id`

Retrieves a specific race by its ID.

**Response (200 OK):**
```json
{
  "id": "4203e29f-06c3-442b-897a-a5ca4469acfd",
  "startTime": "2026-02-13T16:18:02.797Z",
  "status": "pending",
  "participants": ["Alice", "Bob", "Charlie"],
  "createdAt": "2026-02-13T16:18:02.797Z"
}
```

**Error Response (404 Not Found):**
```json
{
  "error": "Not found",
  "message": "Race with id {id} not found"
}
```

### Get All Races

**GET** `/api/race`

Retrieves all races.

**Response (200 OK):**
```json
[
  {
    "id": "4203e29f-06c3-442b-897a-a5ca4469acfd",
    "startTime": "2026-02-13T16:18:02.797Z",
    "status": "pending",
    "participants": ["Alice", "Bob", "Charlie"],
    "createdAt": "2026-02-13T16:18:02.797Z"
  }
]
```

## Testing

Run the test suite:

```bash
npm test
```

## Linting

Check code quality:

```bash
npm run lint
```

## Project Structure

```
ost-expo-game/
├── src/
│   ├── models/
│   │   └── race.ts          # Race data models and interfaces
│   ├── routes/
│   │   ├── race.ts          # Race API routes
│   │   └── race.test.ts     # Route tests
│   ├── services/
│   │   └── raceService.ts   # Race business logic
│   └── server.ts            # Express server configuration
├── dist/                    # Compiled JavaScript (generated)
├── node_modules/            # Dependencies (generated)
├── .eslintrc.js            # ESLint configuration
├── .gitignore              # Git ignore rules
├── jest.config.js          # Jest test configuration
├── package.json            # Node.js project configuration
├── tsconfig.json           # TypeScript configuration
└── README.md               # This file
```

## Technologies Used

- **Node.js**: JavaScript runtime
- **Express**: Web framework
- **TypeScript**: Type-safe JavaScript
- **Jest**: Testing framework
- **Supertest**: HTTP testing library
- **ESLint**: Code linting
- **UUID**: Unique ID generation

## Data Model

### Race

| Field | Type | Description |
|-------|------|-------------|
| id | string | Unique identifier (UUID v4) |
| startTime | Date | When the race was created |
| status | 'pending' \| 'active' \| 'completed' | Current race status |
| participants | string[] | Array of participant names |
| createdAt | Date | Timestamp of race creation |

## License

GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.
