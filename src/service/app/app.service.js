const appRepository = require('../../repository/app.repository')
const { map, flatMap, first } = require('rxjs/operators')
const { from } = require('rxjs')

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

function findRecent() {
    return appRepository.findRecent()
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

exports.findAll = findAll
exports.find = find
exports.findRecent = findRecent
exports.registerView = registerView
exports.findPopular = findPopular