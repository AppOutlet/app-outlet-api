const logger = require('../util/logger')
const syncService = require('../service/sync.service')
const  cron = require('node-cron')

cron.schedule('*/2 * * * *', () => {
    syncService.synchronize()
})
