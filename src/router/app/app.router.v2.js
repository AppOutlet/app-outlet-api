const { Router } = require('express')
const appService = require('../../service/app/app.service')
const HttpStatus = require('http-status-codes')

const router = new Router()

router.get('/search', (request, response) => {
    appService.findV2(request.query).subscribe(apps => {
        response.send(apps)
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

module.exports = router