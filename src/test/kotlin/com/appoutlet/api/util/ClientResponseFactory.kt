package com.appoutlet.api.util

import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.ClientResponse
import reactor.core.publisher.Mono

fun clientResponseFactory(response: String): Mono<ClientResponse> {
	val clientResponse = ClientResponse.create(HttpStatus.OK)
		.header("content-type", "application/json")
		.body(response)
		.build()

	return Mono.just(clientResponse)
}
