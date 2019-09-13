const axios = require('axios')
const { from } = require('rxjs')
const { map } = require('rxjs/operators')
const flatpakBaseUrl = "https://flathub.org/api/v1/apps/"

function getApps() {
    const request = axios.get(flatpakBaseUrl)
    return parseAxiosRequest(request)
}

function getAppDetails(flatpakAppId) {
    const request = axios.get(flatpakBaseUrl + flatpakAppId)
    return parseAxiosRequest(request)
}

function parseAxiosRequest(request) {
    return from(request)
        .pipe(
            map(response => response.data)
        )
}

exports.getApps = getApps
exports.getAppDetails = getAppDetails