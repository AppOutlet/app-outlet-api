const Tag = require('../model/tag')
const { from } = require('rxjs')

exports.save = function(tag) {

    const promise = new Promise((resolve, reject) => {
        Tag.findOneAndUpdate({ _id: tag }, { _id: tag }, { upsert: true }, (err, document) => {
            if (err) {
                reject(err)
            } else {
                resolve(document)
            }
        })
    })

    return from(promise)
}

exports.findAll = function() {

    const promise = Tag.find()
        .sort({ _id: 1 })

    return from(promise)
}