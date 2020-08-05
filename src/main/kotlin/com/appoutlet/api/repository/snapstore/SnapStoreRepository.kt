package com.appoutlet.api.repository.snapstore

import com.appoutlet.api.model.snapstore.SnapStoreApplication
import com.appoutlet.api.model.snapstore.SnapStoreAppsResult
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Repository
class SnapStoreRepository(
	private val snapStoreWebClient: SnapStoreWebClient
) {
	fun getApps(): Flux<SnapStoreApplication> {
		return snapStoreWebClient.webClient
			.get()
			.retrieve()
			.bodyToFlux(SnapStoreAppsResult::class.java)
			.flatMap { Flux.fromIterable(it._embedded.application) }
	}
}
