package com.appoutlet.api.repository.appimagehub

import org.springframework.web.reactive.function.client.WebClient

class AppImageHubWebClient(
    val webClient: WebClient
) {
	companion object {
		const val APP_IMAGE_HUB_URL = "https://appimage.github.io/feed.json"
	}
}
