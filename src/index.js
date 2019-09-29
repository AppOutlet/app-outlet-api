const express = require('express')
const constants = require('./config/constants')
const logger = require('./util/logger')
const router = require('./router')
const config = require('./config')

const app = express()

config(app)

app.use('', router)

app.listen(constants.port, () => {
    logger.i(`Server started at port ${constants.port}`)
})
