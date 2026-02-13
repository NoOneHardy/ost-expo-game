import request from 'supertest';
import app from '../server';

describe('POST /api/race/start', () => {
  it('should create a new race with default values', async () => {
    const response = await request(app)
      .post('/api/race/start')
      .send({})
      .expect(201);

    expect(response.body).toHaveProperty('id');
    expect(response.body).toHaveProperty('startTime');
    expect(response.body).toHaveProperty('status', 'pending');
    expect(response.body).toHaveProperty('participants', []);
    expect(response.body).toHaveProperty('createdAt');
    
    // Verify ID is a valid UUID format
    expect(response.body.id).toMatch(/^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    
    // Verify timestamps are valid ISO strings
    expect(() => new Date(response.body.startTime)).not.toThrow();
    expect(() => new Date(response.body.createdAt)).not.toThrow();
  });

  it('should create a new race with participants', async () => {
    const participants = ['player1', 'player2', 'player3'];
    
    const response = await request(app)
      .post('/api/race/start')
      .send({ participants })
      .expect(201);

    expect(response.body).toHaveProperty('id');
    expect(response.body).toHaveProperty('participants', participants);
    expect(response.body.participants).toHaveLength(3);
  });

  it('should return 400 if participants is not an array', async () => {
    const response = await request(app)
      .post('/api/race/start')
      .send({ participants: 'invalid' })
      .expect(400);

    expect(response.body).toHaveProperty('error', 'Invalid request');
    expect(response.body).toHaveProperty('message', 'participants must be an array');
  });

  it('should create multiple races with unique IDs', async () => {
    const response1 = await request(app)
      .post('/api/race/start')
      .send({})
      .expect(201);

    const response2 = await request(app)
      .post('/api/race/start')
      .send({})
      .expect(201);

    expect(response1.body.id).not.toBe(response2.body.id);
  });
});

describe('GET /api/race/:id', () => {
  it('should retrieve a race by ID', async () => {
    // First create a race
    const createResponse = await request(app)
      .post('/api/race/start')
      .send({ participants: ['player1'] })
      .expect(201);

    const raceId = createResponse.body.id;

    // Then retrieve it
    const getResponse = await request(app)
      .get(`/api/race/${raceId}`)
      .expect(200);

    expect(getResponse.body).toHaveProperty('id', raceId);
    expect(getResponse.body).toHaveProperty('participants', ['player1']);
  });

  it('should return 404 for non-existent race', async () => {
    const response = await request(app)
      .get('/api/race/00000000-0000-0000-0000-000000000000')
      .expect(404);

    expect(response.body).toHaveProperty('error', 'Not found');
  });
});

describe('GET /api/race', () => {
  it('should retrieve all races', async () => {
    // Create a few races
    await request(app).post('/api/race/start').send({});
    await request(app).post('/api/race/start').send({ participants: ['player1'] });

    const response = await request(app)
      .get('/api/race')
      .expect(200);

    expect(Array.isArray(response.body)).toBe(true);
    expect(response.body.length).toBeGreaterThanOrEqual(2);
  });
});

describe('Health check', () => {
  it('should return ok status', async () => {
    const response = await request(app)
      .get('/health')
      .expect(200);

    expect(response.body).toHaveProperty('status', 'ok');
    expect(response.body).toHaveProperty('timestamp');
  });
});
