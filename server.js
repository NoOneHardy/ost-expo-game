const express = require('express');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

// In-memory data storage
let scores = [
  { id: 1, playerId: 'p1', playerName: 'Alice', score: 1250, timestamp: new Date('2026-02-13T14:00:00Z').toISOString() },
  { id: 2, playerId: 'p2', playerName: 'Bob', score: 1100, timestamp: new Date('2026-02-13T14:05:00Z').toISOString() },
  { id: 3, playerId: 'p3', playerName: 'Charlie', score: 950, timestamp: new Date('2026-02-13T14:10:00Z').toISOString() },
  { id: 4, playerId: 'p4', playerName: 'Diana', score: 850, timestamp: new Date('2026-02-13T14:15:00Z').toISOString() },
  { id: 5, playerId: 'p5', playerName: 'Eve', score: 720, timestamp: new Date('2026-02-13T14:20:00Z').toISOString() }
];

let events = [
  { id: 1, playerId: 'p1', playerName: 'Alice', eventType: 'score', points: 100, timestamp: new Date('2026-02-13T15:00:00Z').toISOString(), description: 'Alice scored 100 points' },
  { id: 2, playerId: 'p2', playerName: 'Bob', eventType: 'challenge', points: 50, timestamp: new Date('2026-02-13T15:05:00Z').toISOString(), description: 'Bob completed Challenge 3' },
  { id: 3, playerId: 'p3', playerName: 'Charlie', eventType: 'battle', points: 75, timestamp: new Date('2026-02-13T15:10:00Z').toISOString(), description: 'Charlie won Battle Round' },
  { id: 4, playerId: 'p1', playerName: 'Alice', eventType: 'score', points: 50, timestamp: new Date('2026-02-13T15:15:00Z').toISOString(), description: 'Alice scored 50 points' },
  { id: 5, playerId: 'p4', playerName: 'Diana', eventType: 'achievement', points: 25, timestamp: new Date('2026-02-13T15:20:00Z').toISOString(), description: 'Diana unlocked achievement' }
];

let nextScoreId = scores.length + 1;
let nextEventId = events.length + 1;

// GET /api/scoreboard - Returns latest scoreboard with recent events
app.get('/api/scoreboard', (req, res) => {
  const limit = parseInt(req.query.limit, 10) || 10;
  const eventsLimit = parseInt(req.query.eventsLimit, 10) || 20;
  
  // Sort scores by score descending
  const topScores = [...scores]
    .sort((a, b) => b.score - a.score)
    .slice(0, limit);
  
  // Sort events by timestamp descending (most recent first)
  const recentEvents = [...events]
    .sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp))
    .slice(0, eventsLimit);
  
  res.json({
    scoreboard: topScores,
    recentEvents: recentEvents,
    timestamp: new Date().toISOString()
  });
});

// GET /api/scores - Returns all scores
app.get('/api/scores', (req, res) => {
  const limit = parseInt(req.query.limit, 10) || 10;
  const topScores = [...scores]
    .sort((a, b) => b.score - a.score)
    .slice(0, limit);
  
  res.json(topScores);
});

// GET /api/events - Returns all events
app.get('/api/events', (req, res) => {
  const limit = parseInt(req.query.limit, 10) || 20;
  const recentEvents = [...events]
    .sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp))
    .slice(0, limit);
  
  res.json(recentEvents);
});

// POST /api/score - Add a new score
app.post('/api/score', (req, res) => {
  const { playerId, playerName, score } = req.body;
  
  if (!playerId || !playerName || score === undefined) {
    return res.status(400).json({ error: 'playerId, playerName, and score are required' });
  }
  
  const parsedScore = parseInt(score, 10);
  if (isNaN(parsedScore)) {
    return res.status(400).json({ error: 'score must be a valid number' });
  }
  
  const newScore = {
    id: nextScoreId++,
    playerId,
    playerName,
    score: parsedScore,
    timestamp: new Date().toISOString()
  };
  
  scores.push(newScore);
  
  res.status(201).json(newScore);
});

// POST /api/event - Add a new event
app.post('/api/event', (req, res) => {
  const { playerId, playerName, eventType, points, description } = req.body;
  
  if (!playerId || !playerName || !eventType) {
    return res.status(400).json({ error: 'playerId, playerName, and eventType are required' });
  }
  
  const parsedPoints = points !== undefined ? parseInt(points, 10) : 0;
  if (isNaN(parsedPoints)) {
    return res.status(400).json({ error: 'points must be a valid number' });
  }
  
  const newEvent = {
    id: nextEventId++,
    playerId,
    playerName,
    eventType,
    points: parsedPoints,
    timestamp: new Date().toISOString(),
    description: description || `${playerName} ${eventType}`
  };
  
  events.push(newEvent);
  
  res.status(201).json(newEvent);
});

// Health check endpoint
app.get('/health', (req, res) => {
  res.json({ status: 'ok', timestamp: new Date().toISOString() });
});

// Start the server
app.listen(PORT, () => {
  console.log(`🎮 OST Expo Game API server running on port ${PORT}`);
  console.log(`📊 Scoreboard endpoint: http://localhost:${PORT}/api/scoreboard`);
  console.log(`🏆 Scores endpoint: http://localhost:${PORT}/api/scores`);
  console.log(`📋 Events endpoint: http://localhost:${PORT}/api/events`);
});

module.exports = app;
