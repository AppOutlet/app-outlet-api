const rewire = require('rewire')

const appimageSynchronizer = rewire('./appimage.synchronizer.js')

test('Icon url parsing', () => {
    const getIcon = appimageSynchronizer.__get__('getIcon')
    expect(getIcon({
        icons: [
            'icon.png'
        ]
    })).toBe('https://gitcdn.xyz/repo/AppImage/appimage.github.io/master/database/icon.png')
})

test('Screenshots url parsing', () => {
    const parseScreenshotUrl = appimageSynchronizer.__get__('parseScreenshotUrl')
    expect(parseScreenshotUrl('screenshot.png')).toBe('https://appimage.github.io/database/screenshot.png')
})

test('Parsing screenshots list', () => {
    const getScreenshots = appimageSynchronizer.__get__('getScreenshots')
    expect(getScreenshots({
        screenshots: [
            "screenshot.png",
            "screenshot2.png"
        ]
    })).toEqual([
        'https://appimage.github.io/database/screenshot.png',
        'https://appimage.github.io/database/screenshot2.png'
    ])
})

test('Id generation', () => {
    const generateId = appimageSynchronizer.__get__('generateId')
    const mockApp = {
        name: 'appname',
        authors: [{ name: 'authorname' }]
    }
    expect(generateId(mockApp)).toBe('authorname.appname')
})

test('homepage url generation', () => {
    const getHomepage = appimageSynchronizer.__get__('getHomepage')
    const mockApp = {
        links: [{
            type: 'GitHub',
            url: 'user/repository'
        }]
    }
    expect(getHomepage(mockApp)).toBe('https://github.com/user/repository')
})

test('Bugtracker url generation', () => {
    const getBugtrackerUrl = appimageSynchronizer.__get__('getBugtrackerUrl')
    const mockApp = {
        links: [{
            type: 'GitHub',
            url: 'user/repository'
        }]
    }
    expect(getBugtrackerUrl(mockApp)).toBe('https://github.com/user/repository/issues')
})

test('Get developer name', () => {
    const getDeveloper = appimageSynchronizer.__get__('getDeveloper')
    const mockApp = {
        name: 'appname',
        authors: [{ name: 'authorname' }]
    }
    expect(getDeveloper(mockApp)).toBe('authorname')
})