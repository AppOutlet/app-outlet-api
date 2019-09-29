const logger = require('../../util/logger')
const clientRedis = require('../cache-connection')

const urls = ['/category', '/app']

function cache(request, response, next) {
    const url = request.originalUrl;
    if(urls.includes(url)) {
        clientRedis.get(url, function(err, data) {
            if (data) {
                return response.send(data)
            }
            next()
        })
    }
    next()
}

module.exports = cache
