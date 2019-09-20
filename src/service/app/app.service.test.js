const rewire = require('rewire')

const appServiceRewired = rewire('./app.service.js')

test('Increase views count', () => {
    const increaseViewsCount = appServiceRewired.__get__('increaseViewsCount')
    expect(increaseViewsCount({ views: undefined })).toEqual({ views: 1 })
    expect(increaseViewsCount({ views: null })).toEqual({ views: 1 })
    expect(increaseViewsCount({ views: 0 })).toEqual({ views: 1 })
    expect(increaseViewsCount({ views: 10 })).toEqual({ views: 11 })
    expect(increaseViewsCount({ views: -10 })).toEqual({ views: 1 })
})