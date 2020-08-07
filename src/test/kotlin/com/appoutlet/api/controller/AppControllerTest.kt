package com.appoutlet.api.controller

import com.appoutlet.api.model.ApplicationPackageType
import com.appoutlet.api.model.ApplicationStore
import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.service.application.AppService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux

@WebFluxTest(controllers = [AppController::class])
internal class AppControllerTest {
	@Autowired
	private lateinit var webTestClient: WebTestClient

	@MockkBean
	private lateinit var appServiceMock: AppService

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

		every { appServiceMock.findAll() }.returns(Flux.fromIterable(apps))

		val result = webTestClient.get()
			.uri("/apps")
			.exchange()
			.expectStatus().isOk
			.expectBodyList(AppOutletApplication::class.java)
			.returnResult().responseBody

		assertEquals(apps, result)
	}
}
