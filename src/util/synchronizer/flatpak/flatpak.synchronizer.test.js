const rewire = require('rewire')

const flatpakSynchronizer = rewire('./flatpak.synchronizer.js')

const flatpakApp = {
    bugtrackerUrl: "https://anki.tenderapp.com/discussions/ankidesktop",
    categories: [
        { name: "Audio" },
        { name: "Video" },
        { name: "Development" }
    ],
    currentReleaseDate: "2019-05-20T00:00:00.000Z",
    currentReleaseDescription: "<ul>\n<li>Fix some issues that cause the main window to get stuck</li>\n<li>Fix an empty deck list sometimes appearing when restoring from a backup</li>\n<li>Fix Anki hanging after an error occurs during startup</li>\n<li>Fix error message when syncing with an unconfirmed email address</li>\n<li>Use jsonschema for add-on manifests (thanks to Erez)</li>\n<li>Warn in DB check when high due numbers are encountered</li>\n<li>Improve error messages on full disk and failed add-on deletion</li>\n<li>Fix relearning cards being given learning step count in V2 scheduler</li>\n<li>Fix preview window failing to appear when show both sides enabled</li>\n<li>Removing trailing BR tag when pasting into an empty field</li>\n<li>Don’t throw an error when non-Latin text in the Javascript console can’t be shown</li>\n<li>Double click on add-ons to edit their configuration (thanks to lovac42)</li>\n<li>Fix the window icon in a few screens (thanks to John)</li>\n<li>Improve the default type in the answer note type</li>\n</ul>\n",
    currentReleaseVersion: "2.1.13",
    description: "<p>Anki is a program which makes remembering things easy. Because it's a lot more efficient than traditional study methods, you can either greatly decrease your time spent studying, or greatly increase the amount you learn.</p>\n<p>Anyone who needs to remember things in their daily life can benefit from Anki. Since it is content-agnostic and supports images, audio, videos and scientific markup (via LaTeX), the possibilities are endless.</p>\n",
    developerName: "Damien Elmes",
    donationUrl: "https://apps.ankiweb.net/support/",
    downloadFlatpakRefUrl: "/repo/appstream/net.ankiweb.Anki.flatpakref",
    flatpakAppId: "net.ankiweb.Anki",
    helpUrl: "https://apps.ankiweb.net/docs/manual.html",
    homepageUrl: "https://apps.ankiweb.net/",
    iconDesktopUrl: "/repo/appstream/x86_64/icons/128x128/net.ankiweb.Anki.png",
    iconMobileUrl: "/repo/appstream/x86_64/icons/64x64/net.ankiweb.Anki.png",
    inStoreSinceDate: "2018-03-09T18:04:51.000Z",
    name: "Anki",
    projectLicense: "AGPL-3.0+",
    rating: 0,
    ratingVotes: 0,
    summary: "Powerful, intelligent flash cards",
    translateUrl: "https://apps.ankiweb.net/docs/manual.html#_app_translations",
    screenshots: [
        {
            imgDesktopUrl: "url1",
            imgMobileUrl: "url1Mobile",
            thumbUrl: "url1Thumb"
        },
        {
            imgDesktopUrl: "url2",
            imgMobileUrl: "url2Mobile",
            thumbUrl: "url2Thumb"
        },
        {
            imgDesktopUrl: "url3",
            imgMobileUrl: "url3Mobile",
            thumbUrl: "url3Thumb"
        }
    ]
}

test('Convert Flatpak categories to Outlet categories', () => {
    const convertToOutletCategory = flatpakSynchronizer.__get__('convertToOutletCategory')
    expect(convertToOutletCategory(flatpakApp.categories)).toEqual(["Audio", "Video", "Development"])
})

test('Convert Flatpak screenshots to Outlet screenshots', () => {
    const convertToOutletScreenshots = flatpakSynchronizer.__get__('convertToOutletScreenshots')
    expect(convertToOutletScreenshots(flatpakApp.screenshots)).toEqual(["url1", "url2", "url3"])
})

test('Convert Flatpak app to Outlet app', () => {
    const convertToOutletApp = flatpakSynchronizer.__get__('convertToOutletApp')
    expect(convertToOutletApp(flatpakApp)).toEqual({
        _id: 'net.ankiweb.Anki',
        bugtrackerUrl: 'https://anki.tenderapp.com/discussions/ankidesktop',
        categories: ["Audio", "Video", "Development"],
        developer: 'Damien Elmes',
        donationUrl: 'https://apps.ankiweb.net/support/',
        flatpakAppId: 'net.ankiweb.Anki',
        fullDescription: '<p>Anki is a program which makes remembering things easy. Because it\'s a lot more efficient than traditional study methods, you can either greatly decrease your time spent studying, or greatly increase the amount you learn.</p>\n<p>Anyone who needs to remember things in their daily life can benefit from Anki. Since it is content-agnostic and supports images, audio, videos and scientific markup (via LaTeX), the possibilities are endless.</p>\n',
        homepage: 'https://apps.ankiweb.net/',
        icon: 'https://flathub.org/repo/appstream/x86_64/icons/128x128/net.ankiweb.Anki.png',
        installScript: '',
        lastUpdateDate: new Date('2019-05-20T00:00:00.000Z'),
        license: 'AGPL-3.0+',
        name: 'Anki',
        releaseDate: new Date("2018-03-09T18:04:51.000Z"),
        screenshots: ["url1", "url2", "url3"],
        shortDescription: 'Powerful, intelligent flash cards',
        store: 'flathub',
        type: "Flatpak",
        version: '2.1.13'
    })
})