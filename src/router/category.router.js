const { Router } = require('express')
const categoryService = require('../service/category.service')
const clientRedis = require('../config/cache-connection')
const HttpStatus = require('http-status-codes')
const { map } = require('rxjs/operators')
const router = new Router()

router.get('', (request, response) => {
    categoryService.findAll()
        /*.pipe(
            map(apps => clientRedis.setex(request.originalUrl, 3600, JSON.stringify(apps)))
        )*/.subscribe(apps => {
            response.send(apps)
            }, 
            error => {
                console.log('Deu ruim.')
                response.status(HttpStatus.BAD_REQUEST)
                response.send(error)
        })
})

module.exports = router
