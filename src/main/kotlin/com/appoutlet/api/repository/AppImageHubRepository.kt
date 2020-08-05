package com.appoutlet.api.repository

import com.appoutlet.api.model.appimagehub.AppImageHubApplication
import com.appoutlet.api.model.appimagehub.AppImageHubFeed
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Repository
class AppImageHubRepository {
	fun getApps(): Flux<AppImageHubApplication> {
		// TODO: create a provider to inject this WebClient

		val exchangeStrategies = ExchangeStrategies.builder()
			.codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(1_000_000) }
			.build()

		return WebClient.builder()
			.exchangeStrategies(exchangeStrategies)
			.baseUrl(APP_IMAGE_HUB_URL)
			.build()
			.get()
			.retrieve()
			.bodyToFlux(AppImageHubFeed::class.java)
			.flatMap { Flux.fromIterable(it.items) }
	}

	companion object {
		private const val APP_IMAGE_HUB_URL = "https://appimage.github.io/feed.json"
	}
}
