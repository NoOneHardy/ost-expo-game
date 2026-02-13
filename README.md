# ost-expo-game

The software for the expo game for the OST developed during a hack- and makeathon.

## Features

- **Scoreboard API**: Returns the latest player scores with rankings
- **Recent Events**: Tracks and displays the most recent game events
- **Real-time Updates**: Combined endpoint for scoreboard and events

## Installation

```bash
npm install
```

## Usage

Start the server:

```bash
npm start
```

The server will run on `http://localhost:3000` by default.

## API Endpoints

### GET /api/scoreboard

Returns the latest scoreboard with recent events combined in one response.

**Query Parameters:**
- `limit` (optional): Number of top scores to return (default: 10)
- `eventsLimit` (optional): Number of recent events to return (default: 20)

**Example Request:**
```bash
curl http://localhost:3000/api/scoreboard
```

**Example Response:**
```json
{
  "scoreboard": [
    {
      "id": 1,
      "playerId": "p1",
      "playerName": "Alice",
      "score": 1250,
      "timestamp": "2026-02-13T14:00:00.000Z"
    }
  ],
  "recentEvents": [
    {
      "id": 1,
      "playerId": "p1",
      "playerName": "Alice",
      "eventType": "score",
      "points": 100,
      "timestamp": "2026-02-13T15:00:00.000Z",
      "description": "Alice scored 100 points"
    }
  ],
  "timestamp": "2026-02-13T16:22:00.000Z"
}
```

### GET /api/scores

Returns all scores sorted by score descending.

**Query Parameters:**
- `limit` (optional): Number of scores to return (default: 10)

**Example Request:**
```bash
curl http://localhost:3000/api/scores?limit=5
```

### GET /api/events

Returns all events sorted by timestamp descending (most recent first).

**Query Parameters:**
- `limit` (optional): Number of events to return (default: 20)

**Example Request:**
```bash
curl http://localhost:3000/api/events?limit=10
```

### POST /api/score

Add a new score entry.

**Request Body:**
```json
{
  "playerId": "p6",
  "playerName": "Frank",
  "score": 650
}
```

**Example Request:**
```bash
curl -X POST http://localhost:3000/api/score \
  -H "Content-Type: application/json" \
  -d '{"playerId":"p6","playerName":"Frank","score":650}'
```

### POST /api/event

Add a new event entry.

**Request Body:**
```json
{
  "playerId": "p1",
  "playerName": "Alice",
  "eventType": "achievement",
  "points": 75,
  "description": "Alice completed bonus challenge"
}
```

**Example Request:**
```bash
curl -X POST http://localhost:3000/api/event \
  -H "Content-Type: application/json" \
  -d '{"playerId":"p1","playerName":"Alice","eventType":"achievement","points":75,"description":"Alice completed bonus challenge"}'
```

### GET /health

Health check endpoint.

**Example Request:**
```bash
curl http://localhost:3000/health
```

## Development

The application uses in-memory storage for simplicity. Data is reset when the server restarts.

## Configuration

Set the port via environment variable:

```bash
PORT=8080 npm start
```

## License

ISC

