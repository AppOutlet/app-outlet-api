const App = require('../model/app')
const { from } = require('rxjs')

function findAll() {

    const promise = new Promise((resolve, reject) => {
        App.find((err, response) => {
            if (err) {
                reject(reject)
            } else {
                resolve(response)
            }
        })
    })

    return from(promise)
}

function save(app) {
    const promise = new Promise((resolve, reject) => {
        App.findOneAndUpdate({ _id: app._id }, app, { upsert: true }, (err, document) => {
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