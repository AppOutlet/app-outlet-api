const mongoose = require('mongoose')
const constants = require('./constants')

console.log('Connecting to database')

mongoose.connect(constants.databaseUrl, { useNewUrlParser: true })
mongoose.set('useFindAndModify', false);

const db = mongoose.connection;

db.on('error', function (err) {
    console.error(err)
})

db.once('open', function () {
    console.log('Database connection established')
});