package com.appoutlet.api.service.application

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
}
