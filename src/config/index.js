require('./database-connection')
const syncInteceptor = require('./interceptor/sync.interceptor')
const cors = require('cors')
const bodyParser = require('body-parser')

function config(app) {
    app.use(syncInteceptor)
    app.use(cors())
    app.use(bodyParser.json())
}

module.exports = config
