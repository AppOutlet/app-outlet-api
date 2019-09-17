const { Router } = require('express')
const appRouter = require('./app.router')
const categoryRouter = require('./category.router')

const router = Router()

router.use('/app', appRouter)
router.use('/category', categoryRouter)

module.exports = router