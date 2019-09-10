const flatpakRepository = require('../../repository/flatpak.repository')
const logger = require('../logger')
const { map, flatMap } = require('rxjs/operators')
const { from } = require('rxjs')

function synchronizeFlatpak() {
    logger.v('Initializing flatpak sync')
    return flatpakRepository.getApps()
        .pipe(
            flatMap(from),
            flatMap(app => flatpakRepository.getAppDetails(app.flatpakAppId)),
            map((app) => {
                return app
            })
        )
}

module.exports = synchronizeFlatpak
