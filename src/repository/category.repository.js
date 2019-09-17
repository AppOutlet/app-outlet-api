const Category = require('../model/category')
const { from } = require('rxjs')

function findAll() {
    return from(Category.find())
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

exports.findAll = findAll
exports.save = save