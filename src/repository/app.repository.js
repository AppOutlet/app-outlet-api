const App = require('../model/app')
const { from } = require('rxjs')

exports.findAll = function () {

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