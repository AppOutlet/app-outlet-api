require('./database-connection')
const syncInteceptor = require('./interceptor/sync.interceptor')

function config(app) {
    app.use(syncInteceptor)
}

module.exports = config