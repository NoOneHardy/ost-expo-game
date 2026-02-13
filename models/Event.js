const mongoose = require('mongoose');

const eventSchema = new mongoose.Schema({
  playerId: {
    type: String,
    required: true,
    index: true
  },
  playerName: {
    type: String,
    required: true
  },
  eventType: {
    type: String,
    required: true
  },
  points: {
    type: Number,
    default: 0
  },
  description: {
    type: String,
    default: ''
  },
  timestamp: {
    type: Date,
    default: Date.now
  }
});

eventSchema.index({ timestamp: -1 });

module.exports = mongoose.model('Event', eventSchema);
