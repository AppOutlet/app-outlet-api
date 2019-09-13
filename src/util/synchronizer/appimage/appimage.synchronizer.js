const appImageRepository = require('../../../repository/appimage.repository')
const { flatMap, map } = require('rxjs/operators')
const { from, of } = require('rxjs')

function synchronizeAppImage() {
    return appImageRepository.getApps()
        .pipe(
            flatMap(from),
            flatMap(convertToOutletApp)
        )
}

function convertToOutletApp(appImageApp) {
    return {
        _id: String,
        name: String,
        categories: [String],
        icon: String,
        screenshots: [String],
        shortDescription: String,
        fullDescription: String,
        store: String,
        installScript: String,
        releaseDate: Date,
        lastUpdateDate: Date,
        version: String,
        bugtrackerUrl: String,
        developer: String,
        donationUrl: String,
        flatpakAppId: String,
        homepage: String,
        license: String,
        channel: String
    }
}

module.exports = synchronizeAppImage