const mongoose = require('mongoose')
const Category = require('./category')

const appSchema = mongoose.Schema({
    _id: String,
    name: String,
    categories: [String],
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
    license: String,
    channel: String,
    type: String,
    views: Number,
    storeUrl: String,
    packageName: String,
    downloadLink: String,
    confinement: String
})

const App = mongoose.model('App', appSchema);

module.exports = App
