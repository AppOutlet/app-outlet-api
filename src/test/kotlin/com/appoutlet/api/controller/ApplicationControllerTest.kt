package com.appoutlet.api.controller

import com.appoutlet.api.exception.ApplicationNotFoundException
import com.appoutlet.api.model.ApplicationPackageType
import com.appoutlet.api.model.ApplicationStore
import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.service.application.ApplicationService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [ApplicationController::class])
internal class ApplicationControllerTest {
	@Autowired
	private lateinit var webTestClient: WebTestClient

	@MockkBean
	private lateinit var applicationServiceMock: ApplicationService

	@Test
	fun `Should get all apps `() {
		val apps = listOf(
			AppOutletApplication(
				id = "Application id",
				name = "Application name",
				summary = "Application summary",
				description = "Application description",
				developer = "Application developer",
				store = ApplicationStore.SNAP_STORE,
				packageType = ApplicationPackageType.SNAP
			)
		)

		every { applicationServiceMock.findAll() }.returns(Flux.fromIterable(apps))

		val result = webTestClient.get()
			.uri("/apps")
			.exchange()
			.expectStatus().isOk
			.expectBodyList(AppOutletApplication::class.java)
			.returnResult().responseBody

		assertEquals(apps, result)
	}

	@Test
	fun `Should increment viewCount of a given app `() {
		val app = AppOutletApplication(
			id = "appId",
			name = "Application name",
			summary = "Application summary",
			description = "Application description",
			developer = "Application developer",
			store = ApplicationStore.SNAP_STORE,
			packageType = ApplicationPackageType.SNAP
		)

		every { applicationServiceMock.registerVisualization(any()) }.returns(Mono.just(app))

		val result = webTestClient.post()
			.uri("/apps/appId/view")
			.exchange()
			.expectStatus().isOk
			.expectBody(AppOutletApplication::class.java)
			.returnResult().responseBody

		assertEquals(app, result)
	}

	@Test
	fun `Should return 404 if app not exists `() {
		every { applicationServiceMock.registerVisualization(any()) }.returns(
			Mono.error(ApplicationNotFoundException("App not found"))
		)

		webTestClient.post()
			.uri("/apps/appId/view")
			.exchange()
			.expectStatus().isNotFound
	}
}
