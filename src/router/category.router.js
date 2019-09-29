const { Router } = require('express')
const categoryService = require('../service/category.service')
const HttpStatus = require('http-status-codes')

const router = new Router()

router.get('', (request, response) => {
    categoryService.findAll().subscribe(apps => {
            response.send(apps)
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

module.exports = router
