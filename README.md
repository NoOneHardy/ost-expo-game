# ost-expo-game
The software for the expo game for the OST developed during a hack- and makeathon

## Setup

```bash
npm install
```

## Running the Server

```bash
npm start
```

The server will start on port 3000 (or the PORT environment variable if set).

## API Endpoints

### End a Race

Ends an existing race with a given ID. If a username is provided, an Event is stored.

**Endpoint:** `POST /races/:id/end`

**Request Body:**
- `riskDecision` (boolean, required): Whether a problem happened during the race
- `username` (string, optional): Username associated with the race end
- `email` (string, optional): Email address

**Example Request:**
```bash
curl -X POST http://localhost:3000/races/race_001/end \
  -H "Content-Type: application/json" \
  -d '{
    "riskDecision": true,
    "username": "player123",
    "email": "player@example.com"
  }'
```

**Success Response (200):**
```json
{
  "message": "Race ended successfully",
  "race": {
    "id": "race_001",
    "status": "ended",
    "endedAt": "2024-01-15T10:30:00.000Z",
    "riskDecision": true,
    "email": "player@example.com"
  },
  "event": {
    "id": "event_1705318200000_abc123xyz",
    "time": "2024-01-15T10:30:00.000Z",
    "username": "player123"
  }
}
```

**Error Responses:**
- `400 Bad Request`: Missing or invalid riskDecision
- `404 Not Found`: Race with the given ID does not exist
- `409 Conflict`: Race is already ended

### Get All Races

**Endpoint:** `GET /races`

Returns all races in the system.

### Get All Events

**Endpoint:** `GET /events`

Returns all events stored in the system.

### Health Check

**Endpoint:** `GET /health`

Returns server health status.
