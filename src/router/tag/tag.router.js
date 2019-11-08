const { Router } = require('express')
const tagService = require('../../service/tag/tag.service')
const HttpStatus = require('http-status-codes')

const router = new Router()

router.get('', (request, response) => {
    tagService.findAll().subscribe(tags => {
        response.send(tags)
    }, err => {
        response.status(HttpStatus.BAD_REQUEST)
        response.send(err)
    })
})

module.exports = router