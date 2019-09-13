const mongoose = require('mongoose')

const categorySchema = mongoose.Schema({
    _id: String
})
const Category = mongoose.model('Category', categorySchema);

module.exports = Category