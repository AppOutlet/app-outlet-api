const flatpakRepository = require('../../repository/flatpak.repository')
const logger = require('../logger')

function synchronizeFlatpak() {
    logger.v('Initializing flatpak sync')
    return flatpakRepository.getApps()
}

module.exports = synchronizeFlatpak
