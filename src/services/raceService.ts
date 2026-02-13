import { v4 as uuidv4 } from 'uuid';
import { Race, CreateRaceRequest, RaceResponse } from '../models/race';

// In-memory storage for races
const races = new Map<string, Race>();

export class RaceService {
  /**
   * Create a new race
   */
  createRace(request: CreateRaceRequest): Race {
    const now = new Date();
    const race: Race = {
      id: uuidv4(),
      startTime: now,
      status: 'pending',
      participants: request.participants || [],
      createdAt: now
    };

    races.set(race.id, race);
    return race;
  }

  /**
   * Get a race by ID
   */
  getRaceById(id: string): Race | undefined {
    return races.get(id);
  }

  /**
   * Get all races
   */
  getAllRaces(): Race[] {
    return Array.from(races.values());
  }

  /**
   * Convert Race to RaceResponse
   */
  toRaceResponse(race: Race): RaceResponse {
    return {
      id: race.id,
      startTime: race.startTime.toISOString(),
      status: race.status,
      participants: race.participants,
      createdAt: race.createdAt.toISOString()
    };
  }
}

export const raceService = new RaceService();
