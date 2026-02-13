import { LeaderboardManager } from './leaderboard';
import { ScoreEvent } from './types';

/**
 * Demo script showing how to use the LeaderboardManager
 */
function runDemo() {
  console.log('=== Leaderboard Manager Demo ===\n');

  const manager = new LeaderboardManager();

  // Simulate game events
  console.log('Adding initial events...');
  const initialEvents: ScoreEvent[] = [
    { id: 'e1', playerId: 'Alice', scoreChange: 100, timestamp: Date.now() },
    { id: 'e2', playerId: 'Bob', scoreChange: 150, timestamp: Date.now() + 1 },
    { id: 'e3', playerId: 'Charlie', scoreChange: 75, timestamp: Date.now() + 2 },
  ];

  initialEvents.forEach(event => {
    manager.addEvent(event);
    console.log(`  - ${event.playerId}: +${event.scoreChange}`);
  });

  console.log('\n--- Creating Snapshot ---');
  const snapshot1 = manager.createSnapshot();
  console.log(`Snapshot created at event count: ${snapshot1.eventCount}`);
  console.log('Snapshot scores:', Array.from(snapshot1.playerScores.entries()));

  // Add more events
  console.log('\n--- Adding More Events ---');
  const moreEvents: ScoreEvent[] = [
    { id: 'e4', playerId: 'Alice', scoreChange: 50, timestamp: Date.now() + 3 },
    { id: 'e5', playerId: 'Bob', scoreChange: 25, timestamp: Date.now() + 4 },
    { id: 'e6', playerId: 'David', scoreChange: 200, timestamp: Date.now() + 5 },
    { id: 'e7', playerId: 'Alice', scoreChange: 30, timestamp: Date.now() + 6 },
  ];

  moreEvents.forEach(event => {
    manager.addEvent(event);
    console.log(`  - ${event.playerId}: +${event.scoreChange}`);
  });

  // Check if snapshot is older than 5 events
  console.log('\n--- Checking Snapshot Age ---');
  const isOld = manager.isSnapshotOlderThan(5);
  const totalEvents = manager.getEvents().length;
  const eventsSinceSnapshot = totalEvents - snapshot1.eventCount;
  console.log(`Total events: ${totalEvents}`);
  console.log(`Events since snapshot: ${eventsSinceSnapshot}`);
  console.log(`Is snapshot older than 5 events? ${isOld ? 'YES ✓' : 'NO ✗'}`);

  // Generate leaderboard
  console.log('\n--- Generating Leaderboard ---');
  const leaderboard = manager.generateLeaderboard();
  console.log('Current Leaderboard:');
  leaderboard.forEach(entry => {
    console.log(`  ${entry.rank}. ${entry.playerId}: ${entry.score} points`);
  });

  // Use the combined method
  console.log('\n--- Using checkAndGenerateLeaderboard ---');
  const result = manager.checkAndGenerateLeaderboard(5);
  console.log(`Snapshot needs refresh: ${result.needsNewSnapshot ? 'YES' : 'NO'}`);
  console.log(`Events since last snapshot: ${result.eventsSinceSnapshot}`);
  console.log('\nLeaderboard:');
  result.leaderboard.forEach(entry => {
    console.log(`  ${entry.rank}. ${entry.playerId}: ${entry.score} points`);
  });

  // Add more events to exceed threshold
  console.log('\n--- Adding Even More Events ---');
  const additionalEvents: ScoreEvent[] = [
    { id: 'e8', playerId: 'Charlie', scoreChange: 100, timestamp: Date.now() + 7 },
    { id: 'e9', playerId: 'Bob', scoreChange: 75, timestamp: Date.now() + 8 },
  ];

  additionalEvents.forEach(event => {
    manager.addEvent(event);
    console.log(`  - ${event.playerId}: +${event.scoreChange}`);
  });

  // Check again
  console.log('\n--- Checking Snapshot Age Again ---');
  const result2 = manager.checkAndGenerateLeaderboard(5);
  console.log(`Snapshot is old: ${result2.snapshotIsOld ? 'YES ✓' : 'NO ✗'}`);
  console.log(`Events since last snapshot: ${result2.eventsSinceSnapshot} (threshold: 5)`);
  console.log('This snapshot should be refreshed!');

  console.log('\n--- Creating New Snapshot ---');
  const snapshot2 = manager.createSnapshot();
  console.log(`New snapshot created at event count: ${snapshot2.eventCount}`);

  console.log('\n--- Final Check ---');
  const result3 = manager.checkAndGenerateLeaderboard(5);
  console.log(`Snapshot is old: ${result3.snapshotIsOld ? 'YES' : 'NO ✗'}`);
  console.log(`Events since last snapshot: ${result3.eventsSinceSnapshot}`);
  console.log('\nFinal Leaderboard:');
  result3.leaderboard.forEach(entry => {
    console.log(`  ${entry.rank}. ${entry.playerId}: ${entry.score} points`);
  });

  console.log('\n=== Demo Complete ===');
}

// Run the demo
runDemo();
