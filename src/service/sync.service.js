const logger = require('../util/logger')
const { of, empty } = require('rxjs')
const { map, flatMap } = require('rxjs/operators')
const flatpakSynchronizer = require('../util/synchronizer/flatpak/flatpak.synchronizer')
const snapSynchronizer = require('../util/synchronizer/snap/snap.synchronizer')
const appImageSynchronizer = require('../util/synchronizer/appimage/appimage.synchronizer')
const configService = require('../service/config.service')
const contants = require('../config/constants')

function synchronize() {

    if (!shouldSynchronize()) return

    logger.v('Starting synchronization')

    shouldSynchronize()
        .pipe(
            flatMap(verifyShouldSynchronize),

            map(data => log(data, 'Starting flatpak sync')),
            flatMap(() => flatpakSynchronizer()),

            map(data => log(data, 'Starting Snap sync')),
            flatMap(() => snapSynchronizer()),

            map(data => log(data, 'Starting AppImage sync')),
            flatMap(() => appImageSynchronizer()),

            flatMap(() => configService.notifySyncCompleted())
        ).subscribe(
            () => logger.i('Apps synchronized sucessfully'),
            err => logger.e(err),
            () => logger.i('Synchronization completed')
        )
}

function log(data, message) {
    logger.i(message)
    return data
}

function verifyShouldSynchronize(shouldSynchronize) {
    if (shouldSynchronize) {
        return of(shouldSynchronize)
    } else {
        return empty()
    }
}

function shouldSynchronize() {

    if (!contants.allowSync) {
        return of(false)
    }

    return configService.getConfig().pipe(
        map((config) => {
            if (config) {
                const now = new Date()
                const interval = now.getTime() - config.lastSync.getTime()
                return interval > contants.synchronizationInterval
            } else {
                return true
            }
        }),
        map(shouldSynchronize => {
            logger.i(`Should synchronize: ${shouldSynchronize}`)
            return shouldSynchronize
        })
    )
}

exports.synchronize = synchronize

