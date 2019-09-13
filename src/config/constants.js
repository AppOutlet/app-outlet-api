module.exports = {
    port: process.env.OUTLET_PORT || 3001,
    devMode: process.env.OUTLET_DEV_MODE || true,
    databaseUrl: process.env.OUTLET_DATABASE_URL || '',
    synchronizationInterval: process.env.OUTLET_SYNC_INTERVAL || 1000 * 60 // Default: 1 minute
}