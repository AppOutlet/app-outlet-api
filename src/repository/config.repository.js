const Config = require('../model/config')
const { from } = require('rxjs')

function getConfig() {
    const promise = Config.findOne().exec()
    return from(promise)
}

function save(config) {
    const promise = Config.findOneAndUpdate({ _id: config._id }, config, { upsert: true }).exec()
    return from(promise)
}

exports.getConfig = getConfig
exports.save = save