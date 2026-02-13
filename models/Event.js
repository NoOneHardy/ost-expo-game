/**
 * Event model
 * Represents an event when a race ends with a username
 */
class Event {
  constructor(username) {
    this.id = this.generateId();
    this.time = new Date();
    this.username = username;
  }

  generateId() {
    return `event_${Date.now()}_${Math.random().toString(36).substring(2, 11)}`;
  }
}

module.exports = Event;
