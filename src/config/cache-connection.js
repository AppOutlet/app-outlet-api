const redis = require('redis')
const constants = require('./constants')
const logger = require('../util/logger')

logger.i('Connecting to redis cache')
//const clientRedis = redis.createClient(constants.redisUrl)

/*clientRedis.on('connect', () => {
    logger.i(`Server connect with redis`)
});

clientRedis.on('error', err => {
    logger.i(`Server redis with error: ${err}`)
});*/

//module.exports = clientRedis
