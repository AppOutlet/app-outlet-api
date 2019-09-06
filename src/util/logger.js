const constants = require('../config/constants')

const LogLevel = {
    VERBOSE: 0,
    INF0: 1,
    WARNING: 2,
    ERROR: 3
}

function shouldLog() {
    return constants.devMode
}

exports.e = function (message) {
    if (shouldLog()) {
        console.error(message)
    }
}

exports.i = function (message) {
    if (shouldLog()) {
        console.log(message)
    }
}
