package com.appoutlet.api.repository.flathub

import com.appoutlet.api.model.flathub.FlathubApplication
import com.appoutlet.api.model.flathub.FlathubApplicationDetails
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class FlathubRepository(
	private val flathubWebClient: FlathubWebClient
) {
	fun getApps(): Flux<FlathubApplication> {
		return flathubWebClient.webClient
			.get()
			.retrieve()
			.bodyToFlux(FlathubApplication::class.java)
	}

	fun getApplicationDetails(flatpakId: String): Mono<FlathubApplicationDetails> {
		return flathubWebClient.webClient
			.get()
			.uri("/$flatpakId")
			.retrieve()
			.bodyToMono(FlathubApplicationDetails::class.java)
	}
}
