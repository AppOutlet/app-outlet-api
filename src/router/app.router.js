const { Router } = require('express')
const appService = require('../service/app/app.service')
const HttpStatus = require('http-status-codes')
const clientRedis = require('../config/cache-connection')

const router = new Router()

router.get('', (request, response) => {
    appService.findAll().subscribe(apps => {
        const url = request.originalUrl;
        clientRedis.setex(url, 3600, JSON.stringify(apps))
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

router.get('/recent', (request, response) => {
    appService.findRecentlyUpdated().subscribe(apps => {
        response.send(apps)
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

router.post('/view', (request, response) => {
    appService.registerView(request.body).subscribe(() => {
        response.send()
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

router.get('/popular', (request, response) => {
    appService.findPopular().subscribe(apps => {
        response.send(apps)
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

router.get('/new', (request, response) => {
    appService.findNew().subscribe(apps => {
        response.send(apps)
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

module.exports = router
