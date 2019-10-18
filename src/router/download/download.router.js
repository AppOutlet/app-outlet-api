const { Router } = require('express')
const appService = require('../../service/app/app.service')
const HttpStatus = require('http-status-codes')

const router = new Router()

router.get('/:type', (request, response) => {
    appService.getDownloadUrl(request.params.type).subscribe(url => {
        response.redirect(url)
    }, error => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(error)
    })
})

module.exports = router