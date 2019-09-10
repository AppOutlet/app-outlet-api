const mongoose = require('mongoose')

const appSchema = mongoose.Schema({
    identifier: String,
    name: String,
    category: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Category' }],
    logo: String,
    screens: [String],
    description: String,
    store: String,
    installScript: String,
    creationDate: Date,
    lastUpdate: Date,
    version: String
})

const App = mongoose.model('App', appSchema);

module.exports = App