const { Router } = require('express')
const appRouter = require('./app/app.router')
const categoryRouter = require('./category/category.router')
const downloadRouter = require('./download/download.router')

const router = Router()

router.use('/app', appRouter)
router.use('/category', categoryRouter)
router.use('/download', downloadRouter)
router.get('', (request, response) => {
    response.redirect('https://app-outlet.github.io')
})

module.exports = router