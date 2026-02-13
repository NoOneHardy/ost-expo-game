require('dotenv').config();
const express = require('express');
const cors = require('cors');
const connectDB = require('./db');
const Score = require('./models/Score');
const Event = require('./models/Event');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

// Connect to MongoDB
connectDB();

// Seed data function
async function seedData() {
  try {
    const scoreCount = await Score.countDocuments();
    const eventCount = await Event.countDocuments();
    
    if (scoreCount === 0) {
      const seedScores = [
        { playerId: 'p1', playerName: 'Alice', score: 1250, timestamp: new Date('2026-02-13T14:00:00Z') },
        { playerId: 'p2', playerName: 'Bob', score: 1100, timestamp: new Date('2026-02-13T14:05:00Z') },
        { playerId: 'p3', playerName: 'Charlie', score: 950, timestamp: new Date('2026-02-13T14:10:00Z') },
        { playerId: 'p4', playerName: 'Diana', score: 850, timestamp: new Date('2026-02-13T14:15:00Z') },
        { playerId: 'p5', playerName: 'Eve', score: 720, timestamp: new Date('2026-02-13T14:20:00Z') }
      ];
      await Score.insertMany(seedScores);
      console.log('✅ Seed scores inserted');
    }
    
    if (eventCount === 0) {
      const seedEvents = [
        { playerId: 'p1', playerName: 'Alice', eventType: 'score', points: 100, timestamp: new Date('2026-02-13T15:00:00Z'), description: 'Alice scored 100 points' },
        { playerId: 'p2', playerName: 'Bob', eventType: 'challenge', points: 50, timestamp: new Date('2026-02-13T15:05:00Z'), description: 'Bob completed Challenge 3' },
        { playerId: 'p3', playerName: 'Charlie', eventType: 'battle', points: 75, timestamp: new Date('2026-02-13T15:10:00Z'), description: 'Charlie won Battle Round' },
        { playerId: 'p1', playerName: 'Alice', eventType: 'score', points: 50, timestamp: new Date('2026-02-13T15:15:00Z'), description: 'Alice scored 50 points' },
        { playerId: 'p4', playerName: 'Diana', eventType: 'achievement', points: 25, timestamp: new Date('2026-02-13T15:20:00Z'), description: 'Diana unlocked achievement' }
      ];
      await Event.insertMany(seedEvents);
      console.log('✅ Seed events inserted');
    }
  } catch (error) {
    console.error('❌ Error seeding data:', error.message);
  }
}

seedData();

// GET /api/scoreboard - Returns latest scoreboard with recent events
app.get('/api/scoreboard', async (req, res) => {
  try {
    const limit = parseInt(req.query.limit, 10) || 10;
    const eventsLimit = parseInt(req.query.eventsLimit, 10) || 20;
    
    const topScores = await Score.find()
      .sort({ score: -1 })
      .limit(limit)
      .lean();
    
    const recentEvents = await Event.find()
      .sort({ timestamp: -1 })
      .limit(eventsLimit)
      .lean();
    
    res.json({
      scoreboard: topScores,
      recentEvents: recentEvents,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    console.error('Error fetching scoreboard:', error);
    res.status(500).json({ error: 'Failed to fetch scoreboard' });
  }
});

// GET /api/scores - Returns all scores
app.get('/api/scores', async (req, res) => {
  try {
    const limit = parseInt(req.query.limit, 10) || 10;
    const topScores = await Score.find()
      .sort({ score: -1 })
      .limit(limit)
      .lean();
    
    res.json(topScores);
  } catch (error) {
    console.error('Error fetching scores:', error);
    res.status(500).json({ error: 'Failed to fetch scores' });
  }
});

// GET /api/events - Returns all events
app.get('/api/events', async (req, res) => {
  try {
    const limit = parseInt(req.query.limit, 10) || 20;
    const recentEvents = await Event.find()
      .sort({ timestamp: -1 })
      .limit(limit)
      .lean();
    
    res.json(recentEvents);
  } catch (error) {
    console.error('Error fetching events:', error);
    res.status(500).json({ error: 'Failed to fetch events' });
  }
});

// POST /api/score - Add a new score
app.post('/api/score', async (req, res) => {
  try {
    const { playerId, playerName, score } = req.body;
    
    if (!playerId || !playerName || score === undefined) {
      return res.status(400).json({ error: 'playerId, playerName, and score are required' });
    }
    
    const parsedScore = parseInt(score, 10);
    if (isNaN(parsedScore)) {
      return res.status(400).json({ error: 'score must be a valid number' });
    }
    
    const newScore = new Score({
      playerId,
      playerName,
      score: parsedScore
    });
    
    await newScore.save();
    
    res.status(201).json(newScore);
  } catch (error) {
    console.error('Error creating score:', error);
    res.status(500).json({ error: 'Failed to create score' });
  }
});

// POST /api/event - Add a new event
app.post('/api/event', async (req, res) => {
  try {
    const { playerId, playerName, eventType, points, description } = req.body;
    
    if (!playerId || !playerName || !eventType) {
      return res.status(400).json({ error: 'playerId, playerName, and eventType are required' });
    }
    
    const parsedPoints = points !== undefined ? parseInt(points, 10) : 0;
    if (isNaN(parsedPoints)) {
      return res.status(400).json({ error: 'points must be a valid number' });
    }
    
    const newEvent = new Event({
      playerId,
      playerName,
      eventType,
      points: parsedPoints,
      description: description || `${playerName} ${eventType}`
    });
    
    await newEvent.save();
    
    res.status(201).json(newEvent);
  } catch (error) {
    console.error('Error creating event:', error);
    res.status(500).json({ error: 'Failed to create event' });
  }
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
