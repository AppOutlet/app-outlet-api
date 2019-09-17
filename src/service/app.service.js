const appRepository = require('../repository/app.repository')

function findAll() {
    return appRepository.findAll()
}

function find(query) {
    return appRepository.find(query)
}

exports.findAll = findAll
exports.find = find
