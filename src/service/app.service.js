const appRepository = require('../repository/app.repository')
const { empty } = require('rxjs')

function findAll() {
    return appRepository.findAll()
}

function find(query) {
    if (query.name) {
        return findByName(query.name)
    } else if (query.category) {
        return findByCategory(query.category)
    } else {
        return empty()
    }
}

function findByName(appName) {
    const query = {
        name: {
            $regex: new RegExp(appName, 'i')
        }
    }
    return appRepository.find(query)
}

function findByCategory(category) {
    return empty()
}

exports.findAll = findAll
exports.find = find
