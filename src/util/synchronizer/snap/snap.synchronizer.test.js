const rewire = require('rewire')

const snapSynchronizer = rewire('./snap.synchronizer.js')
const snapApp = {
    aliases: null,
    anon_download_url: 'https://api.snapcraft.io/api/v1/snaps/download/7LBICJlIRP1UEKCIpJUkqhmerY95mhgt_5.snap',
    apps: [
        'btctools'
    ],
    architecture: [
        'amd64'
    ],
    base: 'core18',
    binary_filesize: 28213248,
    channel: 'stable',
    common_ids: [],
    confinement: 'strict',
    contact: 'https://github.com/btccom/libbtctools/issues',
    content: 'application',
    date_published: '2019-05-19T02:20:01.506582Z',
    deltas: [],
    description: 'A tool to scan, configure, reboot and upgrading most of all Antminers and some Avalon miners (矿机扫描、配置、重启和升级工具，适用于几乎所有蚂蚁矿机和部分阿瓦隆矿机).\n\nClick "Install" in the upper right corner to install (点击右上角的“Install”进行安装).\n\nYou can click the BTCTools icon in the launcher to launch it (点击启动器中的BTCTools图标启动本工具). \n\nOr you can run this command (也可以通过以下命令启动):\n\n`btctools`\n\nFix running issue in Ubuntu 16.04 (修复在Ubuntu 16.04中无法启动的问题):\n\n`sudo snap install core`\n\nFix "Network is Not Available" Warning. This warning does not affect the scan, but it cannot automatically import the local network address. (修复“网络不可用”提示，该提示不影响扫描，只是不能自动导入网段信息):\n\n`sudo snap connect btctools:network-observe`\n\nUpgrade your BTCTools (升级BTCTools到新版本):\n\n`snap refresh btctools`',
    developer_id: 'RaUaOLgt5Hur5lX2ePRKbGVWPwuwdd8d',
    developer_name: 'BTC.com',
    developer_validation: 'unproven',
    download_sha3_384: '610556fa9c1674a2d545df4cc3f1193d10976794a70804d30117db601073423c4ecc5abb995779947c9d46a2e0ff09a5',
    download_sha512: '8f2629495772766c9f343357f14516ccc9f6844c33fe73bbb0b0e472508a56272b17d7a1df75d4c449664d9d03eed084f1fe9a31305b1d379aabf8f4c827ba89',
    download_url: 'https://api.snapcraft.io/api/v1/snaps/download/7LBICJlIRP1UEKCIpJUkqhmerY95mhgt_5.snap',
    epoch: '0',
    gated_snap_ids: [],
    icon_url: 'https://dashboard.snapcraft.io/site_media/appmedia/2019/05/icon.svg_wa2MiTP.png',
    last_updated: '2019-06-03T09:25:13.648241+00:00',
    license: 'CC-BY-ND-4.0',
    name: 'btctools.btccom',
    origin: 'btccom',
    package_name: 'btctools',
    prices: {},
    'private': false,
    publisher: 'BTC.com',
    ratings_average: 0,
    release: [
        '16'
    ],
    revision: 5,
    screenshot_urls: [
        'https://dashboard.snapcraft.io/site_media/appmedia/2019/06/%E6%89%B9%E6%B3%A8_2019-06-03_183758.png',
        'https://dashboard.snapcraft.io/site_media/appmedia/2019/05/2019-05-19_11-48-57%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png',
        'https://dashboard.snapcraft.io/site_media/appmedia/2019/05/%E6%89%B9%E6%B3%A8_2019-05-19_112259.png'
    ],
    snap_id: '7LBICJlIRP1UEKCIpJUkqhmerY95mhgt',
    summary: 'An Antminer Management Tool from BTC.com',
    support_url: '',
    title: 'BTCTools',
    version: '1.2.3.2',
    website: 'https://pool.btc.com/tools'
}

test('Convert Snap app to Outlet app', () => {
    const convertToOutletApp = snapSynchronizer.__get__('convertToOutletApp')

    expect(convertToOutletApp(snapApp)).toEqual({
        _id: '7LBICJlIRP1UEKCIpJUkqhmerY95mhgt',
        bugtrackerUrl: '',
        categories: [],
        channel: 'stable',
        developer: 'BTC.com',
        donationUrl: '',
        flatpakAppId: '',
        fullDescription: 'A tool to scan, configure, reboot and upgrading most of all Antminers and some Avalon miners (矿机扫描、配置、重启和升级工具，适用于几乎所有蚂蚁矿机和部分阿瓦隆矿机).\n\nClick "Install" in the upper right corner to install (点击右上角的“Install”进行安装).\n\nYou can click the BTCTools icon in the launcher to launch it (点击启动器中的BTCTools图标启动本工具). \n\nOr you can run this command (也可以通过以下命令启动):\n\n`btctools`\n\nFix running issue in Ubuntu 16.04 (修复在Ubuntu 16.04中无法启动的问题):\n\n`sudo snap install core`\n\nFix "Network is Not Available" Warning. This warning does not affect the scan, but it cannot automatically import the local network address. (修复“网络不可用”提示，该提示不影响扫描，只是不能自动导入网段信息):\n\n`sudo snap connect btctools:network-observe`\n\nUpgrade your BTCTools (升级BTCTools到新版本):\n\n`snap refresh btctools`',
        homepage: 'https://pool.btc.com/tools',
        icon: 'https://dashboard.snapcraft.io/site_media/appmedia/2019/05/icon.svg_wa2MiTP.png',
        installScript: '',
        lastUpdateDate: new Date(snapApp.last_updated),
        license: 'CC-BY-ND-4.0',
        name: 'BTCTools',
        releaseDate: new Date(snapApp.date_published),
        screenshots: [
            'https://dashboard.snapcraft.io/site_media/appmedia/2019/06/%E6%89%B9%E6%B3%A8_2019-06-03_183758.png',
            'https://dashboard.snapcraft.io/site_media/appmedia/2019/05/2019-05-19_11-48-57%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png',
            'https://dashboard.snapcraft.io/site_media/appmedia/2019/05/%E6%89%B9%E6%B3%A8_2019-05-19_112259.png'
        ],
        shortDescription: 'An Antminer Management Tool from BTC.com',
        store: 'snapstore',
        type: "Snap",
        version: '1.2.3.2'
    })
})