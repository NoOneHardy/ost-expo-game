#!/bin/bash
# Test script for race ending API

echo "Starting server..."
npm start &
SERVER_PID=$!
sleep 3

echo -e "\n=== Testing Health Check ==="
curl http://localhost:3000/health
echo -e "\n"

echo -e "\n=== Getting all races ==="
curl http://localhost:3000/races
echo -e "\n"

echo -e "\n=== Test 1: End race with username and email (should create event) ==="
curl -X POST http://localhost:3000/races/race_001/end \
  -H "Content-Type: application/json" \
  -d '{"riskDecision": true, "username": "player123", "email": "player@example.com"}'
echo -e "\n"

echo -e "\n=== Test 2: End race without username (should NOT create event) ==="
curl -X POST http://localhost:3000/races/race_002/end \
  -H "Content-Type: application/json" \
  -d '{"riskDecision": false, "email": "test@example.com"}'
echo -e "\n"

echo -e "\n=== Test 3: Try to end already ended race (should fail with 409) ==="
curl -X POST http://localhost:3000/races/race_001/end \
  -H "Content-Type: application/json" \
  -d '{"riskDecision": true}'
echo -e "\n"

echo -e "\n=== Test 4: Try to end non-existent race (should fail with 404) ==="
curl -X POST http://localhost:3000/races/invalid_race/end \
  -H "Content-Type: application/json" \
  -d '{"riskDecision": true}'
echo -e "\n"

echo -e "\n=== Test 5: Try to end race without riskDecision (should fail with 400) ==="
curl -X POST http://localhost:3000/races/race_003/end \
  -H "Content-Type: application/json" \
  -d '{"username": "test"}'
echo -e "\n"

echo -e "\n=== Getting all events ==="
curl http://localhost:3000/events
echo -e "\n"

echo -e "\n=== Stopping server ==="
kill $SERVER_PID
