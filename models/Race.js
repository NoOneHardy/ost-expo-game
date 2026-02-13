/**
 * Race model
 * Represents a race with its status and metadata
 */
class Race {
  constructor(id) {
    this.id = id;
    this.status = 'active'; // 'active' or 'ended'
    this.createdAt = new Date();
    this.endedAt = null;
    this.riskDecision = null;
    this.email = null;
  }

  /**
   * End the race
   * @param {boolean} riskDecision - Whether a problem happened
   * @param {string} email - Optional email
   */
  end(riskDecision, email = null) {
    this.status = 'ended';
    this.endedAt = new Date();
    this.riskDecision = riskDecision;
    this.email = email;
  }

  isActive() {
    return this.status === 'active';
  }
}

module.exports = Race;
