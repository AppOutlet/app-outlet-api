require('./database-connection')
require('./cache-connection')
require('./sync.cron')
//const cacheInterceptor = require('./interceptor/cache.interceptor')
const cors = require('cors')
const bodyParser = require('body-parser')

function config(app) {
    app.use(cors())
    //app.use(cacheInterceptor)
    app.use(bodyParser.json())
}

module.exports = config
