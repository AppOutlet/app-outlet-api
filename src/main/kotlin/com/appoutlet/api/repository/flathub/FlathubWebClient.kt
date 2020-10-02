package com.appoutlet.api.repository.flathub

import org.springframework.web.reactive.function.client.WebClient

class FlathubWebClient(
    val webClient: WebClient
) {
	companion object {
		const val FLATHUB_API_URL = "https://flathub.org/api/v1/apps"
	}
}
