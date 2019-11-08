const mongoose = require('mongoose')

const tagSchema = mongoose.Schema({
    _id: String
})
const Tag = mongoose.model('Tag', tagSchema);

module.exports = Tag