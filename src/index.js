const express = require('express')
const redis = require('redis')
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

const clientRedis = redis.createClient(constants.redisUrl)

clientRedis.on('connect', () => {
    logger.i(`Server connect with redis`)
});

clientRedis.on('error', err => {
    logger.i(`Server redis with error: ${err}`)
});

module.exports.clientRedis  = clientRedis
