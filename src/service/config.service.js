const configRepository = require('../repository/config.repository')
const { flatMap } = require('rxjs/operators')

function getConfig() {
    return configRepository.getConfig()
}

function notifySyncCompleted() {
    return configRepository.getConfig()
        .pipe(
            flatMap(config => {
                if (config == null) {
                    config = {}
                }
                config.lastSync = new Date()
                return configRepository.save(config)
            })
        )
}

exports.getConfig = getConfig
exports.notifySyncCompleted = notifySyncCompleted