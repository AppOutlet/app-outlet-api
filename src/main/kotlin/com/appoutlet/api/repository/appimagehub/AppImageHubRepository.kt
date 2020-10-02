package com.appoutlet.api.repository.appimagehub

import com.appoutlet.api.model.appimagehub.AppImageHubApplication
import com.appoutlet.api.model.appimagehub.AppImageHubFeed
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class AppImageHubRepository(
    private val appImageHubWebClient: AppImageHubWebClient
) {
	fun getApps(): Flux<AppImageHubApplication> {
		return appImageHubWebClient.webClient
			.get()
			.retrieve()
			.bodyToFlux(AppImageHubFeed::class.java)
			.flatMap { Flux.fromIterable(it.items) }
	}
}
