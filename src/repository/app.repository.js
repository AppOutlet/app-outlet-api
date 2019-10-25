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

function findByName(appName) {
    const query = {
        name: {
            $regex: new RegExp(appName, 'i')
        }
    }
    return find(query)
}

function findByTag(tagsArray) {
    const query = {
        tags: {
            $in: tagsArray
        }
    }
    return find(query)
}

function findRecentlyUpdated() {

    const promise = App.find()
        .sort({ lastUpdateDate: -1 })
        .limit(constants.sectionLimit)
        .exec()

    return from(promise)
}

function findNew() {

    const promise = App.find()
        .sort({ releaseDate: -1 })
        .limit(constants.sectionLimit)
        .exec()

    return from(promise)
}

function findPopular() {

    const promise = App.find()
        .sort({ views: -1 })
        .limit(constants.sectionLimit)
        .exec()

    return from(promise)
}

function findByCategory(category) {
    const query = {
        categories: {
            $all: [category]
        }
    }
    return find(query)
}

exports.findByCategory = findByCategory
exports.findAll = findAll
exports.find = find
exports.save = save
exports.findByName = findByName
exports.findByTag = findByTag
exports.findRecentlyUpdated = findRecentlyUpdated
exports.findPopular = findPopular
exports.findNew = findNew