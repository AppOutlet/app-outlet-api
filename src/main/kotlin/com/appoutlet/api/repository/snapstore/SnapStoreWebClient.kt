package com.appoutlet.api.repository.snapstore

import org.springframework.web.reactive.function.client.WebClient

class SnapStoreWebClient(
    val webClient: WebClient
) {
	companion object {
		const val SNAP_STORE_URL = "https://search.apps.ubuntu.com/api/v1/search"
	}
}
