const { Router } = require('express')
const appService = require('../service/app.service')
const HttpStatus = require('http-status-codes')

const router = new Router()

router.get('', (request, response) => {
    appService.findAll().subscribe(apps => {
        response.send(apps)
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

router.get('/search', (request, response) => {
    appService.find(request.query).subscribe(apps => {
        response.send(apps)
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

module.exports = router