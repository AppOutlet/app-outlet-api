module.exports = {
    port: process.env.OUTLET_PORT || 3001,
    devMode: process.env.OUTLET_DEV_MODE || true,
    databaseUrl: process.env.OUTLET_DATABASE_URL || ''
}