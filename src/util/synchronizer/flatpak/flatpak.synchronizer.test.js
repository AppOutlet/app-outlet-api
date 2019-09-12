const rewire = require('rewire')

const flatpakSynchronizer = rewire('./flatpak.synchronizer.js')

test('Convert Flatpak categories to Outlet categories', () => {

    const convertToOutletCategory = flatpakSynchronizer.__get__('convertToOutletCategory')

    const categoriesList = [
        { name: "Video" },
        { name: "Audio" },
        { name: "Development" }
    ]

    expect(convertToOutletCategory(categoriesList)).toEqual(["Video", "Audio", "Development"])
})

test('Convert Flatpak screenshots to Outlet screenshots', () => {
    const convertToOutletScreenshots = flatpakSynchronizer.__get__('convertToOutletScreenshots')

    const urlList = [
        { imgDesktopUrl: "url1" },
        { imgDesktopUrl: "url2" },
        { imgDesktopUrl: "url3" }
    ]

    expect(convertToOutletScreenshots(urlList)).toEqual(["url1", "url2", "url3"])
})