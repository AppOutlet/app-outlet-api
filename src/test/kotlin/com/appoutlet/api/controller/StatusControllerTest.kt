package com.appoutlet.api.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [StatusController::class])
internal class StatusControllerTest {

	@Autowired
	private lateinit var webTestClient: WebTestClient

	@Test
	fun getStatus() {
		webTestClient.get()
			.uri("/status")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.status").isEqualTo("Running")
	}
}
