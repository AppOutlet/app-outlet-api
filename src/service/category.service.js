const categoryRepository = require('../repository/category.repository')

function findAll() {
    return categoryRepository.findAll()
}

exports.findAll = findAll
