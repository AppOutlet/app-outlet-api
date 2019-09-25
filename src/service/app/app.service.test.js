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

test('Match url with type', () => {
    const isUrlMatchType = appServiceRewired.__get__('isUrlMatchType')
    expect(isUrlMatchType('http://someurl.deb', 'deb')).toBeTruthy()
    expect(isUrlMatchType('http://someurl.someextension', 'deb')).toBeFalsy()
    expect(isUrlMatchType('http://someurl.AppImage', 'appimage')).toBeTruthy()
    expect(isUrlMatchType('http://someurl.tar.gz', 'unpacked')).toBeTruthy()
})

test('Extract download URL from release deb', done => {
    const extractBrowserDownloadUrl = appServiceRewired.__get__('extractBrowserDownloadUrl')
    const releaseUrl = 'https://github.com/app-outlet/app-outlet/releases/tag/v1.0.9'
    const assetDownload = 'https://github.com/app-outlet/app-outlet/releases/download/v1.0.9/app-outlet_1.0.9_amd64.deb'
    const release = {
        html_url: releaseUrl,
        assets: [{
            browser_download_url: assetDownload
        }]
    }
    extractBrowserDownloadUrl(release, 'deb').subscribe(url => {
        expect(url).toBe(assetDownload)
        done()
    })
})

test('Extract download URL from release AppImage', done => {
    const extractBrowserDownloadUrl = appServiceRewired.__get__('extractBrowserDownloadUrl')
    const releaseUrl = 'https://github.com/app-outlet/app-outlet/releases/tag/v1.0.9'
    const assetDownload = 'https://github.com/app-outlet/app-outlet/releases/download/v1.0.9/app-outlet_1.0.9_amd64.AppImage'
    const release = {
        html_url: releaseUrl,
        assets: [{
            browser_download_url: assetDownload
        }]
    }
    extractBrowserDownloadUrl(release, 'appimage').subscribe(url => {
        expect(url).toBe(assetDownload)
        done()
    })
})

test('Extract download URL from release with invalid type', done => {
    const extractBrowserDownloadUrl = appServiceRewired.__get__('extractBrowserDownloadUrl')
    const releaseUrl = 'https://github.com/app-outlet/app-outlet/releases/tag/v1.0.9'
    const assetDownload = 'https://github.com/app-outlet/app-outlet/releases/download/v1.0.9/app-outlet_1.0.9_amd64.AppImage'
    const release = {
        html_url: releaseUrl,
        assets: [{
            browser_download_url: assetDownload
        }]
    }
    extractBrowserDownloadUrl(release, 'invalidType').subscribe(url => {
        expect(url).toBe(releaseUrl)
        done()
    })
})