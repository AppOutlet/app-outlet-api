const { map, flatMap, bufferCount } = require('rxjs/operators')
const { from } = require('rxjs')
const snapRepository = require('../../../repository/snap.repository')
const appRepository = require('../../../repository/app.repository')

function synchronizeSnap() {
    return snapRepository.getApps()
        .pipe(
            flatMap(from),
            map(convertToOutletApp),
            flatMap(appRepository.save),
            bufferCount(Number.MAX_VALUE)
        )
}

function convertToOutletApp(snapApp) {
    return {
        _id: snapApp.snap_id,
        name: snapApp.title,
        categories: [],
        icon: snapApp.icon_url,
        screenshots: snapApp.screenshot_urls,
        shortDescription: snapApp.summary,
        fullDescription: snapApp.description,
        store: 'snapstore',
        type: 'Snap',
        installScript: '',
        releaseDate: new Date(snapApp.date_published),
        lastUpdateDate: new Date(snapApp.last_updated),
        version: snapApp.version,
        bugtrackerUrl: snapApp.support_url,
        developer: snapApp.publisher,
        donationUrl: '',
        flatpakAppId: '',
        homepage: snapApp.website,
        license: snapApp.license,
        channel: snapApp.channel
    }
}

module.exports = synchronizeSnap