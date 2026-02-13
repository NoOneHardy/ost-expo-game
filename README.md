# ost-expo-game

The software for the expo game for the OST developed during a hack- and makeathon.

## Overview

This project implements an event-sourcing based leaderboard system with snapshot functionality for efficient score tracking in games. It allows you to:

- Track player score events
- Create snapshots of the leaderboard state
- Check if snapshots are older than a specified number of events
- Generate complete leaderboards using snapshots and recent events

## Features

- **Event Sourcing**: All score changes are stored as events
- **Snapshot System**: Create snapshots to optimize leaderboard generation
- **Snapshot Age Checking**: Determine if snapshots need refreshing based on event count
- **Efficient Leaderboard Generation**: Combine snapshots with recent events for optimal performance

## Installation

```bash
npm install
```

## Usage

### Basic Example

```typescript
import { LeaderboardManager } from './leaderboard';

const manager = new LeaderboardManager();

// Add score events
manager.addEvent({
  id: 'event-1',
  playerId: 'player-1',
  scoreChange: 100,
  timestamp: Date.now()
});

// Create a snapshot
manager.createSnapshot();

// Check if snapshot is older than 5 events
const isOld = manager.isSnapshotOlderThan(5);

// Generate leaderboard
const leaderboard = manager.generateLeaderboard();
console.log(leaderboard);
```

### Check Snapshot Age and Generate Leaderboard

```typescript
// Combined operation that checks snapshot age and generates leaderboard
const result = manager.checkAndGenerateLeaderboard(5);

console.log(`Snapshot needs refresh: ${result.needsNewSnapshot}`);
console.log(`Events since snapshot: ${result.eventsSinceSnapshot}`);
console.log('Leaderboard:', result.leaderboard);

// If snapshot is old, create a new one
if (result.needsNewSnapshot) {
  manager.createSnapshot();
}
```

## API Reference

### LeaderboardManager

#### Methods

- `addEvent(event: ScoreEvent): void` - Add a new score event
- `createSnapshot(): Snapshot` - Create a snapshot of current state
- `getLastSnapshot(): Snapshot | null` - Get the last created snapshot
- `isSnapshotOlderThan(maxEventAge: number): boolean` - Check if snapshot is older than specified number of events
- `generateLeaderboard(): LeaderboardEntry[]` - Generate complete leaderboard using snapshot and recent events
- `checkAndGenerateLeaderboard(maxEventAge: number)` - Combined operation that checks snapshot age and generates leaderboard

### Types

```typescript
interface ScoreEvent {
  id: string;
  playerId: string;
  scoreChange: number;
  timestamp: number;
}

interface Snapshot {
  id: string;
  playerScores: Map<string, number>;
  timestamp: number;
  eventCount: number;
}

interface LeaderboardEntry {
  playerId: string;
  score: number;
  rank: number;
}
```

## Scripts

- `npm run build` - Compile TypeScript to JavaScript
- `npm test` - Run tests with Jest
- `npm run demo` - Run the demo script

## Development

The project uses:
- TypeScript for type-safe code
- Jest for testing
- Event sourcing pattern for data management

## Testing

Run the test suite:

```bash
npm test
```

All tests validate:
- Event management
- Snapshot creation and retrieval
- Snapshot age checking logic
- Leaderboard generation from snapshots and events

## Demo

Run the demo to see the system in action:

```bash
npm run demo
```

The demo shows:
1. Adding initial events
2. Creating snapshots
3. Checking snapshot age against a threshold
4. Generating leaderboards
5. Handling snapshot refresh when threshold is exceeded

## License

ISC

