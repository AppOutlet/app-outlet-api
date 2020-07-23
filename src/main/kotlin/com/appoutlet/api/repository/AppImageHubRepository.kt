package com.appoutlet.api.repository

import com.appoutlet.api.model.AppImageHubFeed
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Repository
class AppImageHubRepository {
	fun getApps(): Mono<AppImageHubFeed>{
		return WebClient.create(APP_IMAGE_HUB_URL)
			.get()
			.retrieve()
			.bodyToMono(AppImageHubFeed::class.java)
	}

	companion object {
		private const val APP_IMAGE_HUB_URL = "https://appimage.github.io/feed.json"
	}
}
