const axios = require('axios')
const { from } = require('rxjs')
const { map } = require('rxjs/operators')

const snapUrl = 'https://search.apps.ubuntu.com/api/v1/search'

function getApps() {
    const promise = axios.get(snapUrl)
    return from(promise)
        .pipe(
            map(parseAxiosResponse),
            map(getAppList)
        )
}

function parseAxiosResponse(response) {
    return response.data
}

function getAppList(response) {
    return response._embedded["clickindex:package"]
}

exports.getApps = getApps