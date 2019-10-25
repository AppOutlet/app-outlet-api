const Category = require('../model/category')
const { from } = require('rxjs')

function findAll() {
    const promise = Category.find()
        .sort({ _id: 1 })
    return from(promise)
}

function save(category) {
    const promise = new Promise((resolve, reject) => {
        Category.findOneAndUpdate({ _id: category }, { _id: category }, { upsert: true }, (err, document) => {
            if (err) {
                reject(err)
            } else {
                resolve(document)
            }
        })
    })

    return from(promise)
}

function getTagsByCategory(category) {}

exports.findAll = findAll
exports.save = save