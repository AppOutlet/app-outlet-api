const mongoose = require('mongoose')
const Category = require('./category')

const appSchema = mongoose.Schema({
    _id: String,
    name: String,
    category: [Category.schema],
    icon: String,
    screenshots: [String],
    shortDescription: String,
    fullDescription: String,
    store: String,
    installScript: String,
    releaseDate: Date,
    lastUpdateDate: Date,
    version: String,
    bugtrackerUrl: String,
    developer: String,
    donationUrl: String,
    flatpakAppId: String,
    homepage: String,
    license: String
})

const App = mongoose.model('App', appSchema);

module.exports = App