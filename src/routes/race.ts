import { Router, Request, Response } from 'express';
import { raceService } from '../services/raceService';
import { CreateRaceRequest } from '../models/race';

const router = Router();

/**
 * POST /api/race/start
 * Create a new race
 */
router.post('/start', (req: Request, res: Response) => {
  try {
    const createRequest: CreateRaceRequest = {
      participants: req.body.participants || []
    };

    // Validate participants array if provided
    if (createRequest.participants && !Array.isArray(createRequest.participants)) {
      return res.status(400).json({
        error: 'Invalid request',
        message: 'participants must be an array'
      });
    }

    // Create the race
    const race = raceService.createRace(createRequest);
    const response = raceService.toRaceResponse(race);

    return res.status(201).json(response);
  } catch (error) {
    console.error('Error creating race:', error);
    return res.status(500).json({
      error: 'Internal server error',
      message: 'Failed to create race'
    });
  }
});

/**
 * GET /api/race/:id
 * Get a race by ID
 */
router.get('/:id', (req: Request, res: Response) => {
  const { id } = req.params;
  const race = raceService.getRaceById(id);

  if (!race) {
    return res.status(404).json({
      error: 'Not found',
      message: `Race with id ${id} not found`
    });
  }

  const response = raceService.toRaceResponse(race);
  return res.status(200).json(response);
});

/**
 * GET /api/race
 * Get all races
 */
router.get('/', (_req: Request, res: Response) => {
  const races = raceService.getAllRaces();
  const response = races.map(race => raceService.toRaceResponse(race));
  return res.status(200).json(response);
});

export default router;
