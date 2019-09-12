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