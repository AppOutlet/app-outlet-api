const categoryRepository = require('../repository/category.repository')
const categories = require('../util/categories')

function findAll() {
    return categoryRepository.findAll()
}

function getTagsFromCategory(categoryName) {

    let category = categories.newCategories.find(category => {
        return category.name == categoryName
    })

    if (category) {
        return category.tags
    } else {
        return []
    }
}

exports.findAll = findAll
exports.getTagsFromCategory = getTagsFromCategory