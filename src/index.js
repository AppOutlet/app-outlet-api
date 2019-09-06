const express = require('express')
const constants = require('./config/constants')
const logger = require('./util/logger')

const app = express()

app.listen(constants.port, () => {
    logger.i(`Server started at port ${constants.port}`)
})