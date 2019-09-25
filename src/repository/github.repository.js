const axios = require('axios')
const { from } = require('rxjs')
const { map } = require('rxjs/operators')

const latestReleaseEndpoint = 'https://api.github.com/repos/app-outlet/app-outlet/releases/latest'

function getLatestRelease() {
    let promise = axios.get(latestReleaseEndpoint)
    return from(promise)
        .pipe(
            map(rawData => rawData.data)
        )
}

exports.getLatestRelease = getLatestRelease