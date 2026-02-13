/**
 * Represents a player's score event in the game
 */
export interface ScoreEvent {
  /** Unique identifier for the event */
  id: string;
  /** Player identifier */
  playerId: string;
  /** Score change (can be positive or negative) */
  scoreChange: number;
  /** Timestamp when the event occurred */
  timestamp: number;
}

/**
 * Represents a player's entry in the leaderboard
 */
export interface LeaderboardEntry {
  /** Player identifier */
  playerId: string;
  /** Total score */
  score: number;
  /** Player's rank (1-indexed) */
  rank: number;
}

/**
 * Represents a snapshot of the leaderboard at a specific point in time
 */
export interface Snapshot {
  /** Unique identifier for the snapshot */
  id: string;
  /** Map of player IDs to their scores at the time of snapshot */
  playerScores: Map<string, number>;
  /** Timestamp when the snapshot was created */
  timestamp: number;
  /** Number of events processed up to this snapshot */
  eventCount: number;
}
