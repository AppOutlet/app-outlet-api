const axios = require('axios')
const { from } = require('rxjs')
const flatpackBaseUrl = "https://flathub.org/api/v1/apps/"

function getApps() {
    const request = axios.get(flatpackBaseUrl)
    return from(request)
}

exports.getApps = getApps