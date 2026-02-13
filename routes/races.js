/**
 * Routes for race endpoints
 */
const express = require('express');
const router = express.Router();
const Event = require('../models/Event');
const dataStore = require('../storage/dataStore');

/**
 * POST /races/:id/end
 * End an existing race with a given ID
 * 
 * Request body:
 * - riskDecision: boolean (required) - Whether a problem happened
 * - username: string (optional) - Username associated with the race end
 * - email: string (optional) - Email address
 * 
 * Response:
 * - 200: Race ended successfully
 * - 400: Invalid request (missing riskDecision or invalid type)
 * - 404: Race not found
 * - 409: Race already ended
 */
router.post('/races/:id/end', (req, res) => {
  const raceId = req.params.id;
  const { riskDecision, username, email } = req.body;

  // Validate required field
  if (typeof riskDecision !== 'boolean') {
    return res.status(400).json({
      error: 'Bad Request',
      message: 'riskDecision is required and must be a boolean'
    });
  }

  // Get the race
  const race = dataStore.getRace(raceId);
  
  if (!race) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Race with id ${raceId} not found`
    });
  }

  // Check if race is already ended
  if (!race.isActive()) {
    return res.status(409).json({
      error: 'Conflict',
      message: `Race with id ${raceId} is already ended`
    });
  }

  // End the race
  race.end(riskDecision, email);

  // If username is provided, create and store an event
  const event = username ? new Event(username) : null;
  if (event) {
    dataStore.saveEvent(event);
  }

  // Respond with success
  res.status(200).json({
    message: 'Race ended successfully',
    race: {
      id: race.id,
      status: race.status,
      endedAt: race.endedAt,
      riskDecision: race.riskDecision,
      email: race.email
    },
    event: event ? {
      id: event.id,
      time: event.time,
      username: event.username
    } : null
  });
});

/**
 * GET /races
 * Get all races (helper endpoint for testing)
 */
router.get('/races', (req, res) => {
  const races = dataStore.getAllRaces();
  res.json({ races });
});

/**
 * GET /events
 * Get all events (helper endpoint for testing)
 */
router.get('/events', (req, res) => {
  const events = dataStore.getAllEvents();
  res.json({ events });
});

module.exports = router;
