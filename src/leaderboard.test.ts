import { LeaderboardManager } from './leaderboard';
import { ScoreEvent } from './types';

describe('LeaderboardManager', () => {
  let manager: LeaderboardManager;

  beforeEach(() => {
    manager = new LeaderboardManager();
  });

  describe('Event Management', () => {
    it('should add events correctly', () => {
      const event: ScoreEvent = {
        id: 'event-1',
        playerId: 'player-1',
        scoreChange: 100,
        timestamp: Date.now(),
      };

      manager.addEvent(event);
      const events = manager.getEvents();

      expect(events).toHaveLength(1);
      expect(events[0]).toEqual(event);
    });

    it('should add multiple events', () => {
      const events: ScoreEvent[] = [
        { id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() },
        { id: 'e2', playerId: 'p2', scoreChange: 200, timestamp: Date.now() },
        { id: 'e3', playerId: 'p1', scoreChange: 50, timestamp: Date.now() },
      ];

      events.forEach(e => manager.addEvent(e));
      expect(manager.getEvents()).toHaveLength(3);
    });
  });

  describe('Snapshot Creation', () => {
    it('should create a snapshot from events', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p2', scoreChange: 200, timestamp: Date.now() });

      const snapshot = manager.createSnapshot();

      expect(snapshot).toBeDefined();
      expect(snapshot.playerScores.get('p1')).toBe(100);
      expect(snapshot.playerScores.get('p2')).toBe(200);
      expect(snapshot.eventCount).toBe(2);
    });

    it('should aggregate multiple events for same player', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p1', scoreChange: 50, timestamp: Date.now() });
      manager.addEvent({ id: 'e3', playerId: 'p1', scoreChange: -20, timestamp: Date.now() });

      const snapshot = manager.createSnapshot();

      expect(snapshot.playerScores.get('p1')).toBe(130);
    });

    it('should return null when no snapshot exists', () => {
      expect(manager.getLastSnapshot()).toBeNull();
    });

    it('should return last snapshot after creation', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.createSnapshot();

      expect(manager.getLastSnapshot()).not.toBeNull();
    });
  });

  describe('isSnapshotOlderThan', () => {
    it('should return true when no snapshot exists', () => {
      expect(manager.isSnapshotOlderThan(5)).toBe(true);
    });

    it('should return false when events since snapshot are less than threshold', () => {
      // Add 3 events and create snapshot
      for (let i = 0; i < 3; i++) {
        manager.addEvent({
          id: `e${i}`,
          playerId: `p${i}`,
          scoreChange: 100,
          timestamp: Date.now(),
        });
      }
      manager.createSnapshot();

      // Add 2 more events (total 5, but only 2 since snapshot)
      manager.addEvent({ id: 'e3', playerId: 'p3', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e4', playerId: 'p4', scoreChange: 100, timestamp: Date.now() });

      // Should not be older than 5
      expect(manager.isSnapshotOlderThan(5)).toBe(false);
    });

    it('should return false when events since snapshot equal threshold', () => {
      // Create snapshot after 2 events
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p2', scoreChange: 100, timestamp: Date.now() });
      manager.createSnapshot();

      // Add exactly 5 more events
      for (let i = 0; i < 5; i++) {
        manager.addEvent({
          id: `e${i + 3}`,
          playerId: `p${i}`,
          scoreChange: 50,
          timestamp: Date.now(),
        });
      }

      // Should not be older (5 events == threshold of 5)
      expect(manager.isSnapshotOlderThan(5)).toBe(false);
    });

    it('should return true when events since snapshot exceed threshold', () => {
      // Create snapshot after 1 event
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.createSnapshot();

      // Add 6 more events (exceeds threshold of 5)
      for (let i = 0; i < 6; i++) {
        manager.addEvent({
          id: `e${i + 2}`,
          playerId: `p${i}`,
          scoreChange: 50,
          timestamp: Date.now(),
        });
      }

      // Should be older (6 events > threshold of 5)
      expect(manager.isSnapshotOlderThan(5)).toBe(true);
    });

    it('should work with threshold of 0', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.createSnapshot();

      // Any new event makes it old with threshold 0
      manager.addEvent({ id: 'e2', playerId: 'p2', scoreChange: 100, timestamp: Date.now() });
      expect(manager.isSnapshotOlderThan(0)).toBe(true);
    });
  });

  describe('generateLeaderboard', () => {
    it('should generate leaderboard from events when no snapshot exists', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p2', scoreChange: 200, timestamp: Date.now() });
      manager.addEvent({ id: 'e3', playerId: 'p3', scoreChange: 150, timestamp: Date.now() });

      const leaderboard = manager.generateLeaderboard();

      expect(leaderboard).toHaveLength(3);
      expect(leaderboard[0]).toEqual({ playerId: 'p2', score: 200, rank: 1 });
      expect(leaderboard[1]).toEqual({ playerId: 'p3', score: 150, rank: 2 });
      expect(leaderboard[2]).toEqual({ playerId: 'p1', score: 100, rank: 3 });
    });

    it('should generate leaderboard using snapshot and recent events', () => {
      // Add events and create snapshot
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p2', scoreChange: 200, timestamp: Date.now() });
      manager.createSnapshot();

      // Add new events after snapshot
      manager.addEvent({ id: 'e3', playerId: 'p1', scoreChange: 150, timestamp: Date.now() });
      manager.addEvent({ id: 'e4', playerId: 'p3', scoreChange: 300, timestamp: Date.now() });

      const leaderboard = manager.generateLeaderboard();

      expect(leaderboard).toHaveLength(3);
      expect(leaderboard[0]).toEqual({ playerId: 'p3', score: 300, rank: 1 });
      expect(leaderboard[1]).toEqual({ playerId: 'p1', score: 250, rank: 2 });
      expect(leaderboard[2]).toEqual({ playerId: 'p2', score: 200, rank: 3 });
    });

    it('should handle negative scores', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p1', scoreChange: -150, timestamp: Date.now() });

      const leaderboard = manager.generateLeaderboard();

      expect(leaderboard).toHaveLength(1);
      expect(leaderboard[0]).toEqual({ playerId: 'p1', score: -50, rank: 1 });
    });

    it('should return empty leaderboard when no events', () => {
      const leaderboard = manager.generateLeaderboard();
      expect(leaderboard).toHaveLength(0);
    });

    it('should handle tie scores with consistent ranking', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p2', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e3', playerId: 'p3', scoreChange: 200, timestamp: Date.now() });

      const leaderboard = manager.generateLeaderboard();

      expect(leaderboard).toHaveLength(3);
      expect(leaderboard[0]?.score).toBe(200);
      expect(leaderboard[0]?.rank).toBe(1);
      expect(leaderboard[1]?.score).toBe(100);
      expect(leaderboard[1]?.rank).toBe(2);
      expect(leaderboard[2]?.score).toBe(100);
      expect(leaderboard[2]?.rank).toBe(3);
    });
  });

  describe('checkAndGenerateLeaderboard', () => {
    it('should indicate snapshot is old when no snapshot exists', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });

      const result = manager.checkAndGenerateLeaderboard(5);

      expect(result.snapshotIsOld).toBe(true);
      expect(result.needsNewSnapshot).toBe(true);
      expect(result.eventsSinceSnapshot).toBe(1);
      expect(result.leaderboard).toHaveLength(1);
    });

    it('should indicate snapshot is not old when within threshold', () => {
      // Create snapshot with 2 events
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p2', scoreChange: 200, timestamp: Date.now() });
      manager.createSnapshot();

      // Add 3 more events (below threshold of 5)
      manager.addEvent({ id: 'e3', playerId: 'p3', scoreChange: 150, timestamp: Date.now() });
      manager.addEvent({ id: 'e4', playerId: 'p1', scoreChange: 50, timestamp: Date.now() });
      manager.addEvent({ id: 'e5', playerId: 'p2', scoreChange: 75, timestamp: Date.now() });

      const result = manager.checkAndGenerateLeaderboard(5);

      expect(result.snapshotIsOld).toBe(false);
      expect(result.needsNewSnapshot).toBe(false);
      expect(result.eventsSinceSnapshot).toBe(3);
      expect(result.leaderboard).toHaveLength(3);
    });

    it('should indicate snapshot is old when exceeds threshold', () => {
      // Create snapshot with 1 event
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.createSnapshot();

      // Add 6 more events (exceeds threshold of 5)
      for (let i = 0; i < 6; i++) {
        manager.addEvent({
          id: `e${i + 2}`,
          playerId: `p${i % 3}`,
          scoreChange: 50,
          timestamp: Date.now(),
        });
      }

      const result = manager.checkAndGenerateLeaderboard(5);

      expect(result.snapshotIsOld).toBe(true);
      expect(result.needsNewSnapshot).toBe(true);
      expect(result.eventsSinceSnapshot).toBe(6);
      expect(result.leaderboard.length).toBeGreaterThan(0);
    });

    it('should generate correct leaderboard regardless of snapshot age', () => {
      manager.addEvent({ id: 'e1', playerId: 'p1', scoreChange: 100, timestamp: Date.now() });
      manager.addEvent({ id: 'e2', playerId: 'p2', scoreChange: 200, timestamp: Date.now() });
      manager.createSnapshot();

      manager.addEvent({ id: 'e3', playerId: 'p1', scoreChange: 150, timestamp: Date.now() });

      const result = manager.checkAndGenerateLeaderboard(5);

      expect(result.leaderboard[0]).toEqual({ playerId: 'p1', score: 250, rank: 1 });
      expect(result.leaderboard[1]).toEqual({ playerId: 'p2', score: 200, rank: 2 });
    });
  });
});
