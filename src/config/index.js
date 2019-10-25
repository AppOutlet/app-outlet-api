require('./database-connection')
require('./sync.cron')
const cors = require('cors')
const bodyParser = require('body-parser')

function config(app) {
    app.use(cors())
    app.use(bodyParser.json())
}

module.exports = config