const App = require('../model/app')
const { from } = require('rxjs')
const constants = require('../config/constants')

function findAll() {

    const promise = new Promise((resolve, reject) => {
        App.find((err, response) => {
            if (err) {
                reject(reject)
            } else {
                resolve(response)
            }
        }).limit(constants.searchLimit)
    })

    return from(promise)
}

function find(query) {
    const promise = new Promise((resolve, reject) => {
        App.find(query, (err, response) => {
            if (err) {
                reject(reject)
            } else {
                resolve(response)
            }
        }).limit(constants.searchLimit)
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
exports.find = find
exports.save = save