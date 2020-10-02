package com.appoutlet.api.repository.snapstore

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class SnapStoreWebClientProvider {
	@Bean
	fun providesSnapStoreWebClient(): SnapStoreWebClient {
		val exchangeStrategies = ExchangeStrategies.builder()
			.codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(8_000_000) }
			.build()

		val webClient = WebClient.builder()
			.exchangeStrategies(exchangeStrategies)
			.baseUrl(SnapStoreWebClient.SNAP_STORE_URL)
			.build()

		return SnapStoreWebClient(webClient)
	}
}
