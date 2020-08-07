package com.appoutlet.api.service.synchronization

import com.appoutlet.api.model.appimagehub.AppImageHubApplication
import com.appoutlet.api.model.appimagehub.AppImageHubAuthor
import com.appoutlet.api.model.appimagehub.AppImageHubLink
import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.repository.AppOutletApplicationRepository
import com.appoutlet.api.repository.appimagehub.AppImageHubRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal class AppImageHubSynchronizerTest {
	private lateinit var appImageHubSynchronizer: AppImageHubSynchronizer
	private val appImageHubRepositoryMock = mockk<AppImageHubRepository>() // TODO: rename all mocks to this pattern
	private val appOutletApplicationRepositoryMock = mockk<AppOutletApplicationRepository>()
	private val synchronizationPropertiesMock = mockk<SynchronizationProperties>()

	@BeforeEach
	fun setup() {
		every { synchronizationPropertiesMock.appImageHub.enabled } returns false
		appImageHubSynchronizer = AppImageHubSynchronizer(
			appImageHubRepositoryMock,
			appOutletApplicationRepositoryMock,
			synchronizationPropertiesMock
		)
	}

    @Test
    fun `Should not synchronize if the property is false `() {
		every { synchronizationPropertiesMock.appImageHub.enabled } returns false
		assertFalse(appImageHubSynchronizer.synchronize().block() == true)
    }

	@Test
	fun `Should synchronize if the property is true `() {
		val applications = listOf(
			AppImageHubApplication(
				authors = listOf(AppImageHubAuthor("Author name")),
				name = "Application name",
				description = "Application description",
				license = "MIT",
				links = listOf(AppImageHubLink(AppImageHubLink.Type.GITHUB, "app-outlet/app-outlet")),
				icons = listOf("path/icon.png"),
				screenshots = listOf("path/screenshot.png"),
				categories = listOf("Category")
			)
		)

		val appOutletApplicationMock = mockk<AppOutletApplication>()

		every { synchronizationPropertiesMock.appImageHub.enabled } returns true
		every { appImageHubRepositoryMock.getApps() }.returns(Flux.fromIterable(applications))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.returns(
			Mono.just(appOutletApplicationMock)
		)

		assertTrue(appImageHubSynchronizer.synchronize().block() == true)
	}
}
