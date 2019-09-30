const logger = require('../util/logger')
const constants = require('./constants')
const syncService = require('../service/sync.service')
const  cron = require('node-cron')

cron.schedule(constants.cronTimer, () => {
    syncService.synchronize()
})
