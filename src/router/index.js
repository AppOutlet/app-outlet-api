const { Router } = require('express')
const appRouter = require('./app/app.router')
const categoryRouter = require('./category/category.router')
const downloadRouter = require('./download/download.router')
const appRouterV2 = require('./app/app.router.v2')
const tagRouter = require('./tag/tag.router')

const router = Router()

router.use('/app', appRouter)
router.use('/v2/app', appRouterV2)
router.use('/category', categoryRouter)
router.use('/tag', tagRouter)
router.use('/download', downloadRouter)
router.get('', (request, response) => {
    response.redirect('https://app-outlet.github.io')
})

module.exports = router