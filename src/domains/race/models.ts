/**
 * Enum representing the risk decision levels a participant can choose
 */
export enum RiskDecision {
  LOW = 'low',
  HIGH = 'high'
}

/**
 * Interface representing a participant in a race
 */
export interface Participant {
  /**
   * Unique identifier for the participant
   */
  id: string;

  /**
   * Email address of the participant
   */
  email: string;

  /**
   * Risk decision made by the participant (low or high)
   */
  riskDecision: RiskDecision;

  /**
   * Flag indicating whether problems occurred during the participant's race
   */
  problemsHappened: boolean;

  /**
   * Timestamp when the participant finished their race
   */
  endTime: Date;
}

/**
 * Interface representing a race event
 */
export interface Race {
  /**
   * Unique identifier for the race
   */
  id: string;

  /**
   * Timestamp when the race started
   */
  startTime: Date;

  /**
   * List of participants in this race
   */
  participants: Participant[];
}
