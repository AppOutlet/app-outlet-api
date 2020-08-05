package com.appoutlet.api.repository.flathub

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class FlatHubWebClientProvider {
	 @Bean
	 fun provideFlathubWebClient(): FlathubWebClient {
		 val webClient = WebClient.create(FlathubWebClient.FLATHUB_API_URL)
		 return FlathubWebClient(webClient)
	 }
}
