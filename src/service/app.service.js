const appRepository = require('../repository/app.repository')
const { empty } = require('rxjs')

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
    return appRepository.find()
}

exports.findAll = findAll
exports.find = find
exports.findRecent = findRecent