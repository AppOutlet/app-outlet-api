const tagRepository = require('../../repository/tag.repository')

exports.findAll = function() {
    return tagRepository.findAll()
}

exports.save = function(tag) {
    return tagRepository.save(tag)
}