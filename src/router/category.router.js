const { Router } = require('express')
const categoryService = require('../service/category.service')
const HttpStatus = require('http-status-codes')
const redis = require('../index')

const router = new Router()

router.get('', (request, response) => {
    const category = 'category'
    redis.clientRedis.get(category, function(err, categories) {
        if (categories) {
            console.log('category by cache')
            return response.send(categories)
        }
        categoryService.findAll().subscribe(apps => {
            console.log('category by server')
            redis.clientRedis.setex(category, 3600, JSON.stringify(apps))
            response.send(apps)
        }, error => {
            response.status(HttpStatus.BAD_REQUEST)
            response.send(error)
        })
    })
})

module.exports = router
