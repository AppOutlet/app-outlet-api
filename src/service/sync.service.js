const logger = require('../util/logger')
const flatpakSynchronizer = require('../util/synchronizer/flatpack.synchronizer')

function synchronize() {

    if (!shouldSynchronize()) return
    logger.v('Starting synchronization')

    flatpakSynchronizer().subscribe(
        data => logger.i('Apps synchronized sucessfully'),
        err => logger.e(err),
        () => logger.i('Synchronization completed')
    )
}

function shouldSynchronize() {
    return true
}

exports.synchronize = synchronize

