const Category = require('../model/category')
const { from } = require('rxjs')

exports.findAll = function () {
    return from(Category.find())
}
