# Race Domain

This module defines the race domain for the OST Expo Game.

## Overview

The race domain consists of two main entities: **Race** and **Participant**.

## Models

### Race

Represents a race event with the following properties:

- `id` (string): Unique identifier for the race
- `startTime` (Date): Timestamp when the race started
- `participants` (Participant[]): Array of participants in the race

### Participant

Represents a participant in a race with the following properties:

- `id` (string): Unique identifier for the participant
- `email` (string): Email address of the participant
- `riskDecision` (RiskDecision): The risk level chosen by the participant (LOW or HIGH)
- `problemsHappened` (boolean): Flag indicating whether problems occurred during the race
- `endTime` (Date): Timestamp when the participant finished their race

### RiskDecision

An enum with two possible values:
- `LOW` = 'low'
- `HIGH` = 'high'

## Usage

```typescript
import { Race, Participant, RiskDecision } from './domains/race';

// Create a participant
const participant: Participant = {
  id: 'participant-123',
  email: 'user@example.com',
  riskDecision: RiskDecision.LOW,
  problemsHappened: false,
  endTime: new Date('2026-02-13T16:30:00Z')
};

// Create a race
const race: Race = {
  id: 'race-001',
  startTime: new Date('2026-02-13T16:00:00Z'),
  participants: [participant]
};
```

## Testing

Run tests with:

```bash
npm test
```

To run only the race domain tests:

```bash
npm test -- src/domains/race
```
