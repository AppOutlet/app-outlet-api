package com.appoutlet.api.repository

import com.appoutlet.api.model.flathub.FlathubApplication
import com.appoutlet.api.model.flathub.FlathubApplicationDetails
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class FlathubRepository {
	fun getApps(): Flux<FlathubApplication> {
		return WebClient.create(FLATHUB_API_URL)
			.get()
			.retrieve()
			.bodyToFlux(FlathubApplication::class.java)
	}

	fun getApplicationDetails(flatpakId: String): Mono<FlathubApplicationDetails> {
		return WebClient.create("$FLATHUB_API_URL/$flatpakId")
			.get()
			.retrieve()
			.bodyToMono(FlathubApplicationDetails::class.java)
	}

	companion object {
		private const val FLATHUB_API_URL = "https://flathub.org/api/v1/apps"
	}
}
