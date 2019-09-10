const mongoose = require('mongoose')

const syncSchema = mongoose.Schema({
    date: Number,
    success: Boolean,
    errorDescription: String
})
const Sync = mongoose.model('Sync', syncSchema);

module.exports = Sync