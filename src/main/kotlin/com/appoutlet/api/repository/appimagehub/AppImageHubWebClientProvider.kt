package com.appoutlet.api.repository.appimagehub

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppImageHubWebClientProvider {
	@Bean
	fun providesAppImageHubWebClient(): AppImageHubWebClient {
		val exchangeStrategies = ExchangeStrategies.builder()
			.codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(1_000_000) }
			.build()

		val webClient = WebClient.builder()
			.exchangeStrategies(exchangeStrategies)
			.baseUrl(AppImageHubWebClient.APP_IMAGE_HUB_URL)
			.build()

		return AppImageHubWebClient(webClient)
	}
}
