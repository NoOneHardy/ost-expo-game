import { Race, Participant, RiskDecision } from './models';

describe('Race Domain Models', () => {
  describe('RiskDecision enum', () => {
    it('should have LOW value', () => {
      expect(RiskDecision.LOW).toBe('low');
    });

    it('should have HIGH value', () => {
      expect(RiskDecision.HIGH).toBe('high');
    });
  });

  describe('Participant interface', () => {
    it('should create a valid participant object', () => {
      const participant: Participant = {
        id: 'participant-123',
        email: 'test@example.com',
        riskDecision: RiskDecision.LOW,
        problemsHappened: false,
        endTime: new Date('2026-02-13T16:30:00Z')
      };

      expect(participant.id).toBe('participant-123');
      expect(participant.email).toBe('test@example.com');
      expect(participant.riskDecision).toBe(RiskDecision.LOW);
      expect(participant.problemsHappened).toBe(false);
      expect(participant.endTime).toBeInstanceOf(Date);
    });

    it('should support HIGH risk decision', () => {
      const participant: Participant = {
        id: 'participant-456',
        email: 'highrisk@example.com',
        riskDecision: RiskDecision.HIGH,
        problemsHappened: true,
        endTime: new Date('2026-02-13T16:45:00Z')
      };

      expect(participant.riskDecision).toBe(RiskDecision.HIGH);
      expect(participant.problemsHappened).toBe(true);
    });
  });

  describe('Race interface', () => {
    it('should create a valid race object with no participants', () => {
      const race: Race = {
        id: 'race-001',
        startTime: new Date('2026-02-13T16:00:00Z'),
        participants: []
      };

      expect(race.id).toBe('race-001');
      expect(race.startTime).toBeInstanceOf(Date);
      expect(race.participants).toEqual([]);
    });

    it('should create a race with multiple participants', () => {
      const participant1: Participant = {
        id: 'participant-1',
        email: 'user1@example.com',
        riskDecision: RiskDecision.LOW,
        problemsHappened: false,
        endTime: new Date('2026-02-13T16:20:00Z')
      };

      const participant2: Participant = {
        id: 'participant-2',
        email: 'user2@example.com',
        riskDecision: RiskDecision.HIGH,
        problemsHappened: true,
        endTime: new Date('2026-02-13T16:25:00Z')
      };

      const race: Race = {
        id: 'race-002',
        startTime: new Date('2026-02-13T16:00:00Z'),
        participants: [participant1, participant2]
      };

      expect(race.participants).toHaveLength(2);
      expect(race.participants[0].id).toBe('participant-1');
      expect(race.participants[1].id).toBe('participant-2');
    });

    it('should maintain all participant details in a race', () => {
      const participant: Participant = {
        id: 'p-789',
        email: 'detailed@example.com',
        riskDecision: RiskDecision.HIGH,
        problemsHappened: false,
        endTime: new Date('2026-02-13T17:00:00Z')
      };

      const race: Race = {
        id: 'race-003',
        startTime: new Date('2026-02-13T16:30:00Z'),
        participants: [participant]
      };

      expect(race.participants[0].email).toBe('detailed@example.com');
      expect(race.participants[0].riskDecision).toBe(RiskDecision.HIGH);
      expect(race.participants[0].problemsHappened).toBe(false);
      expect(race.participants[0].endTime.toISOString()).toBe('2026-02-13T17:00:00.000Z');
    });
  });
});
