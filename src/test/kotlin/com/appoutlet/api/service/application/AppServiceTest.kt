package com.appoutlet.api.service.application

import com.appoutlet.api.exception.ApplicationNotFoundException
import com.appoutlet.api.model.ApplicationPackageType
import com.appoutlet.api.model.ApplicationStore
import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.repository.AppOutletApplicationRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.test.test

internal class AppServiceTest {
	private lateinit var appService: AppService
	private val appOutletApplicationRepositoryMock = mockk<AppOutletApplicationRepository>()

	@BeforeEach
	fun setup() {
		appService = AppService(appOutletApplicationRepositoryMock)
	}

	@Test
	fun findAll() {
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

		every { appOutletApplicationRepositoryMock.findAll() }.returns(Flux.fromIterable(apps))

		assertEquals(apps, appService.findAll().buffer().blockFirst())
	}

	@Test
	fun `Should find by id `() {
		val app1 = AppOutletApplication(
			id = "app1",
			name = "Application name",
			summary = "Application summary",
			description = "Application description",
			developer = "Application developer",
			store = ApplicationStore.SNAP_STORE,
			packageType = ApplicationPackageType.SNAP
		)

		val app2 = AppOutletApplication(
			id = "app2",
			name = "Application name",
			summary = "Application summary",
			description = "Application description",
			developer = "Application developer",
			store = ApplicationStore.SNAP_STORE,
			packageType = ApplicationPackageType.SNAP,
			viewCount = 0
		)

		val app3 = AppOutletApplication(
			id = "app3",
			name = "Application name",
			summary = "Application summary",
			description = "Application description",
			developer = "Application developer",
			store = ApplicationStore.SNAP_STORE,
			packageType = ApplicationPackageType.SNAP,
			viewCount = 9
		)

		every { appOutletApplicationRepositoryMock.findById("app1") }.returns(app1.toMono())
		every { appOutletApplicationRepositoryMock.findById("app2") }.returns(app2.toMono())
		every { appOutletApplicationRepositoryMock.findById("app3") }.returns(app3.toMono())
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.answers {
			return@answers (it.invocation.args[0] as AppOutletApplication).toMono()
		}

		val savedApp1 = appService.registerVisualization("app1").block()
		val savedApp2 = appService.registerVisualization("app2").block()
		val savedApp3 = appService.registerVisualization("app3").block()

		assertEquals(1, savedApp1?.viewCount)
		assertEquals(1, savedApp2?.viewCount)
		assertEquals(10, savedApp3?.viewCount)
	}

	@Test
	fun `Register app view should return error if application not exists `() {
		every { appOutletApplicationRepositoryMock.findById(any<String>()) }.returns(Mono.empty())

		appService.registerVisualization("")
			.test()
			.expectError(ApplicationNotFoundException::class.java)
			.verify()
	}
}
