const mongoose = require('mongoose')
const constants = require('./constants')
const logger = require('../util/logger')

logger.i('Connecting to database')

mongoose.connect(constants.databaseUrl, { useNewUrlParser: true })
mongoose.set('useFindAndModify', false);

const db = mongoose.connection;

db.on('error', function (err) {
    logger.e('Database connection failed')
    process.exit()
})

db.once('open', function () {
    logger.i('Database connection established')
});