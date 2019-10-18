const flatpakRepository = require('../../../repository/flatpak.repository')
const appRepository = require('../../../repository/app.repository')
const categoryRepository = require('../../../repository/category.repository')
const { map, flatMap, bufferCount, filter } = require('rxjs/operators')
const { from } = require('rxjs')

function synchronizeFlatpak() {
    return flatpakRepository.getApps()
        .pipe(
            flatMap(from),
            flatMap(app => flatpakRepository.getAppDetails(app.flatpakAppId)),
            map(convertToOutletApp),
            flatMap(saveCategories),
            flatMap(appRepository.save),
            bufferCount(Number.MAX_VALUE)
        )
}

function saveCategories(outletApp) {
    return from(outletApp.tags)
        .pipe(
            filter(category => category != null),
            flatMap(categoryRepository.save),
            bufferCount(outletApp.tags.length),
            map(() => outletApp)
        )
}

function generateIconUrl(iconUri) {
    if (iconUri && isValidURL(iconUri)) {
        return iconUri
    } else {
        return `https://flathub.org${iconUri}`
    }
}

function isValidURL(url) {
    return /^(?:\w+:)?\/\/([^\s\.]+\.\S{2}|localhost[\:?\d]*)\S*$/.test(url);
}


function convertToOutletApp(flatpakApp) {
    return {
        _id: flatpakApp.flatpakAppId,
        name: flatpakApp.name,
        tags: convertToOutletCategory(flatpakApp.categories),
        icon: generateIconUrl(flatpakApp.iconDesktopUrl),
        screenshots: convertToOutletScreenshots(flatpakApp.screenshots),
        shortDescription: flatpakApp.summary,
        fullDescription: flatpakApp.description,
        store: 'flathub',
        type: 'Flatpak',
        installScript: '',
        releaseDate: new Date(flatpakApp.inStoreSinceDate),
        lastUpdateDate: new Date(flatpakApp.currentReleaseDate),
        version: flatpakApp.currentReleaseVersion,
        bugtrackerUrl: flatpakApp.bugtrackerUrl,
        developer: flatpakApp.developerName,
        donationUrl: flatpakApp.donationUrl,
        flatpakAppId: flatpakApp.flatpakAppId,
        homepage: flatpakApp.homepageUrl,
        license: flatpakApp.projectLicense,
        storeUrl: getStoreUrl(flatpakApp)
    }
}

function convertToOutletCategory(flatpakCategories) {
    const categories = []
    flatpakCategories.forEach(category => {
        categories.push(category.name)
    })
    return categories
}

function convertToOutletScreenshots(flatpakAppScreenshots) {
    const screenshots = []
    flatpakAppScreenshots.forEach(screenshot => {
        screenshots.push(screenshot.imgDesktopUrl)
    })
    return screenshots
}

function getStoreUrl(flatpakApp) {
    return `https://flathub.org/apps/details/${flatpakApp.flatpakAppId}`
}

module.exports = synchronizeFlatpak