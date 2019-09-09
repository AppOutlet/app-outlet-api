const appRepository = require('../repository/app.repository')

function findAll() {
    return appRepository.findAll()
}

exports.findAll = findAll
