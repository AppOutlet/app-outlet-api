const mongoose = require('mongoose')

const configSchema = mongoose.Schema({
    lastSync: Date
})
const Config = mongoose.model('Config', configSchema);

module.exports = Config