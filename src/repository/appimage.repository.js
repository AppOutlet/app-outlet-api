const axios = require('axios')
const { from } = require('rxjs')
const { map } = require('rxjs/operators')

const appImageUrl = 'https://appimage.github.io/feed.json'

function getApps() {
    return from(axios.get(appImageUrl))
        .pipe(
            map(response => {
                return response.data.items
            })
        )
}

exports.getApps = getApps