const mongoose = require('mongoose');

const scoreSchema = new mongoose.Schema({
  playerId: {
    type: String,
    required: true,
    index: true
  },
  playerName: {
    type: String,
    required: true
  },
  score: {
    type: Number,
    required: true,
    min: 0
  },
  timestamp: {
    type: Date,
    default: Date.now
  }
});

scoreSchema.index({ score: -1 });

module.exports = mongoose.model('Score', scoreSchema);
