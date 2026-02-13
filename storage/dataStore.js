/**
 * Simple in-memory data store for races and events
 */
const Race = require('../models/Race');

class DataStore {
  constructor() {
    this.races = new Map();
    this.events = [];
    this.initializeSampleData();
  }

  /**
   * Initialize with sample races for testing
   */
  initializeSampleData() {
    const race1 = new Race('race_001');
    const race2 = new Race('race_002');
    const race3 = new Race('race_003');
    
    this.races.set(race1.id, race1);
    this.races.set(race2.id, race2);
    this.races.set(race3.id, race3);
  }

  /**
   * Get a race by ID
   * @param {string} id - Race ID
   * @returns {Race|undefined}
   */
  getRace(id) {
    return this.races.get(id);
  }

  /**
   * Save an event
   * @param {Event} event - Event to save
   */
  saveEvent(event) {
    this.events.push(event);
  }

  /**
   * Get all events
   * @returns {Array<Event>}
   */
  getAllEvents() {
    return this.events;
  }

  /**
   * Get all races
   * @returns {Array<Race>}
   */
  getAllRaces() {
    return Array.from(this.races.values());
  }
}

// Singleton instance
const dataStore = new DataStore();

module.exports = dataStore;
