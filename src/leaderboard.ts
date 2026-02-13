import { ScoreEvent, Snapshot, LeaderboardEntry } from './types';

/**
 * LeaderboardManager handles snapshot creation, event processing,
 * and leaderboard generation using event sourcing pattern
 */
export class LeaderboardManager {
  private events: ScoreEvent[] = [];
  private lastSnapshot: Snapshot | null = null;

  /**
   * Add a new score event
   */
  addEvent(event: ScoreEvent): void {
    this.events.push(event);
  }

  /**
   * Get all events
   */
  getEvents(): ScoreEvent[] {
    return [...this.events];
  }

  /**
   * Create a snapshot from current state
   */
  createSnapshot(): Snapshot {
    const playerScores = new Map<string, number>();
    
    // Process all events to build current state
    for (const event of this.events) {
      const currentScore = playerScores.get(event.playerId) || 0;
      playerScores.set(event.playerId, currentScore + event.scoreChange);
    }

    this.lastSnapshot = {
      id: `snapshot-${Date.now()}`,
      playerScores,
      timestamp: Date.now(),
      eventCount: this.events.length,
    };

    return this.lastSnapshot;
  }

  /**
   * Get the last snapshot
   */
  getLastSnapshot(): Snapshot | null {
    return this.lastSnapshot;
  }

  /**
   * Check if the last snapshot is older than a specified number of events
   * @param maxEventAge Maximum number of events before snapshot is considered old
   * @returns true if snapshot is older than maxEventAge events, false otherwise
   */
  isSnapshotOlderThan(maxEventAge: number): boolean {
    if (!this.lastSnapshot) {
      return true; // No snapshot exists, so it's considered "old"
    }

    const currentEventCount = this.events.length;
    const snapshotEventCount = this.lastSnapshot.eventCount;
    const eventsSinceSnapshot = currentEventCount - snapshotEventCount;

    return eventsSinceSnapshot > maxEventAge;
  }

  /**
   * Generate a complete leaderboard using the last snapshot and recent events
   * @returns Array of leaderboard entries sorted by score (highest first)
   */
  generateLeaderboard(): LeaderboardEntry[] {
    const playerScores = new Map<string, number>();

    // Start with snapshot data if available
    if (this.lastSnapshot) {
      // Copy snapshot scores
      for (const [playerId, score] of this.lastSnapshot.playerScores.entries()) {
        playerScores.set(playerId, score);
      }

      // Apply events after the snapshot
      const eventsAfterSnapshot = this.events.slice(this.lastSnapshot.eventCount);
      for (const event of eventsAfterSnapshot) {
        const currentScore = playerScores.get(event.playerId) || 0;
        playerScores.set(event.playerId, currentScore + event.scoreChange);
      }
    } else {
      // No snapshot exists, process all events
      for (const event of this.events) {
        const currentScore = playerScores.get(event.playerId) || 0;
        playerScores.set(event.playerId, currentScore + event.scoreChange);
      }
    }

    // Convert to array and sort by score (descending)
    const entries: LeaderboardEntry[] = Array.from(playerScores.entries())
      .map(([playerId, score]) => ({
        playerId,
        score,
        rank: 0, // Will be set below
      }))
      .sort((a, b) => b.score - a.score);

    // Assign ranks
    entries.forEach((entry, index) => {
      entry.rank = index + 1;
    });

    return entries;
  }

  /**
   * Check if snapshot needs refresh and generate leaderboard
   * @param maxEventAge Maximum number of events before snapshot is considered old
   * @returns Object containing snapshot age status and current leaderboard
   */
  checkAndGenerateLeaderboard(maxEventAge: number): {
    snapshotIsOld: boolean;
    eventsSinceSnapshot: number;
    leaderboard: LeaderboardEntry[];
    needsNewSnapshot: boolean;
  } {
    const snapshotIsOld = this.isSnapshotOlderThan(maxEventAge);
    const eventsSinceSnapshot = this.lastSnapshot
      ? this.events.length - this.lastSnapshot.eventCount
      : this.events.length;

    return {
      snapshotIsOld,
      eventsSinceSnapshot,
      leaderboard: this.generateLeaderboard(),
      needsNewSnapshot: snapshotIsOld,
    };
  }
}
