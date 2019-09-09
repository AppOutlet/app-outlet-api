const mongoose = require('mongoose')
const Category = require('./category')

const appSchema = mongoose.Schema({
    name: String,
    category: [String],
    logo: String,
    screens: [String],
    description: String,
    store: String,
    installScript: String
})

const App = mongoose.model('App', appSchema);

module.exports = App