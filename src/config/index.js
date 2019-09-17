require('./database-connection')
const syncInteceptor = require('./interceptor/sync.interceptor')
const cors = require('cors')

function config(app) {
    app.use(syncInteceptor)
    app.use(cors())
}

module.exports = config