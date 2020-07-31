package com.appoutlet.api.repository

import com.appoutlet.api.model.snapstore.SnapStoreApplication
import com.appoutlet.api.model.snapstore.SnapStoreAppsResult
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Repository
class SnapStoreRepository {
	fun getApps(): Flux<SnapStoreApplication> {
		// TODO: create a provider to inject this WebClient

		val exchangeStrategies = ExchangeStrategies.builder()
			.codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(8_000_000) }
			.build()

		return WebClient.builder()
			.exchangeStrategies(exchangeStrategies)
			.baseUrl(SNAP_STORE_URL)
			.build()
			.get()
			.retrieve()
			.bodyToFlux(SnapStoreAppsResult::class.java)
			.flatMap { Flux.fromIterable(it._embedded.application) }
	}

	companion object {
		private const val SNAP_STORE_URL = "https://search.apps.ubuntu.com/api/v1/search"
	}
}
