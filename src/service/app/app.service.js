const appRepository = require('../../repository/app.repository')
const githubRepository = require('../../repository/github.repository')
const { map, flatMap, first, filter, defaultIfEmpty } = require('rxjs/operators')
const { from, of } = require('rxjs')

function findAll() {
    return appRepository.findAll()
}

function find(query) {
    if (query.name) {
        return appRepository.findByName(query.name)
    } else if (query.category) {
        return appRepository.findByCategory(query.category)
    } else {
        return appRepository.find(query)
    }
}

function findRecentlyUpdated() {
    return appRepository.findRecentlyUpdated()
}

function registerView({ id }) {
    return appRepository.find({ _id: id })
        .pipe(
            flatMap(from),
            first(),
            map(increaseViewsCount),
            flatMap(appRepository.save)
        )
}

function increaseViewsCount(app) {
    let currentCount = app.views
    if (currentCount == null || currentCount < 0) {
        currentCount = 0
    }
    app.views = ++currentCount
    return app
}

function findPopular() {
    return appRepository.findPopular()
}

function findNew() {
    return appRepository.findNew()
}

function getDownloadUrl(type) {
    return githubRepository.getLatestRelease().pipe(
        flatMap(release => extractBrowserDownloadUrl(release, type))
    )
}

function extractBrowserDownloadUrl(release, type) {
    return from(release.assets).pipe(
        map(extractAssetDownloadUrl),
        filter(url => isUrlMatchType(url, type)),
        defaultIfEmpty(release.html_url),
        first()
    )
}

function isUrlMatchType(url, type) {
    let extension
    switch (type) {
        case 'deb':
            extension = '.deb'
            break;
        case 'appimage':
            extension = '.AppImage'
            break;
        case 'unpacked':
            extension = '.tar.gz'
            break;
        default:
            extension = null
            break;
    }

    if (extension) {
        return url.endsWith(extension)
    } else {
        return false
    }
}

function extractAssetDownloadUrl(asset) {
    return asset.browser_download_url
}

exports.findAll = findAll
exports.find = find
exports.findRecentlyUpdated = findRecentlyUpdated
exports.registerView = registerView
exports.findPopular = findPopular
exports.findNew = findNew
exports.getDownloadUrl = getDownloadUrl