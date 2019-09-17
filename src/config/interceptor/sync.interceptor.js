const logger = require('../../util/logger')
const syncService = require('../../service/sync.service')

function sync(request, response, next) {
    next()
    syncService.synchronize()
}

module.exports = sync