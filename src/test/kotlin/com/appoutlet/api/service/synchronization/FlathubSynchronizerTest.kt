package com.appoutlet.api.service.synchronization

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.model.flathub.FlathubApplication
import com.appoutlet.api.model.flathub.FlathubApplicationDetails
import com.appoutlet.api.model.flathub.FlathubCategory
import com.appoutlet.api.model.flathub.FlathubScreenshot
import com.appoutlet.api.repository.AppOutletApplicationRepository
import com.appoutlet.api.repository.flathub.FlathubRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.Date

@DisplayName("Flathub synchronizer")
internal class FlathubSynchronizerTest {

	private val flathubRepositoryMock = mockk<FlathubRepository>()
	private val appOutletApplicationRepositoryMock = mockk<AppOutletApplicationRepository>()
	private val synchronizationPropertiesMock = mockk<SynchronizationProperties>()

	private fun getFlathubSynchronizer() = FlathubSynchronizer(
		flathubRepositoryMock,
		appOutletApplicationRepositoryMock,
		synchronizationPropertiesMock
	)

	@Test
	fun `Should synchronize `() {
		val flatpakApp1 = "flatpak.app.1"

		val applications = listOf(
			FlathubApplication(flatpakAppId = flatpakApp1)
		)

		val applicationDetails = FlathubApplicationDetails(
			flatpakAppId = flatpakApp1,
			name = "Application name",
			summary = "Application summary",
			description = "Application description",
			developerName = "Developer",
			projectLicense = "MIT",
			homepageUrl = "https://application.com",
			bugtrackerUrl = "https://application.com/issues",
			donationUrl = "https://application.com/donate",
			iconDesktopUrl = "/file.png",
			downloadFlatpakRefUrl = "/application.flatpakref",
			currentReleaseVersion = "1.0",
			currentReleaseDate = Date(),
			inStoreSinceDate = Date(),
			categories = listOf(FlathubCategory("Category1")),
			screenshots = listOf(
				FlathubScreenshot(imgDesktopUrl = "https://path.to/screenshot1.png")
			)
		)

		every { synchronizationPropertiesMock.flathub.enabled }.returns(true)
		every { flathubRepositoryMock.getApps() }.returns(Flux.fromIterable(applications))
		every { flathubRepositoryMock.getApplicationDetails(flatpakApp1) }.returns(Mono.just(applicationDetails))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.answers {
			val appOutletApp = it.invocation.args[0] as AppOutletApplication

			assertEquals("https://dl.flathub.org/file.png", appOutletApp.icon)
			assertEquals("https://dl.flathub.org/application.flatpakref", appOutletApp.downloadUrl)
			assertEquals(listOf("Category1"), appOutletApp.tags)
			assertEquals(listOf("https://path.to/screenshot1.png"), appOutletApp.screenshots)

			return@answers appOutletApp.toMono()
		}

		assertTrue(getFlathubSynchronizer().synchronize().block() == true)
	}

	@Test
	fun `Should add null on urls if it is invalid URIs`() {

		val applicationDetails = FlathubApplicationDetails(
			flatpakAppId = "flatpakApp1",
			name = "Application name",
			summary = "Application summary",
			description = "Application description",
			developerName = "Developer",
			projectLicense = "MIT",
			homepageUrl = "https://application.com",
			bugtrackerUrl = "https://application.com/issues",
			donationUrl = "https://application.com/donate",
			iconDesktopUrl = "",
			downloadFlatpakRefUrl = "invalid uri",
			currentReleaseVersion = "1.0",
			currentReleaseDate = Date(),
			inStoreSinceDate = Date(),
			categories = listOf(FlathubCategory("Category1")),
			screenshots = listOf(
				FlathubScreenshot(imgDesktopUrl = "https://path.to/screenshot1.png")
			)
		)

		val applications = listOf(
			FlathubApplication(flatpakAppId = applicationDetails.flatpakAppId)
		)



		every { synchronizationPropertiesMock.flathub.enabled }.returns(true)
		every { flathubRepositoryMock.getApps() }.returns(Flux.fromIterable(applications))
		every { flathubRepositoryMock.getApplicationDetails(applicationDetails.flatpakAppId) }.returns(Mono.just(applicationDetails))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.answers {
			val appOutletApp = it.invocation.args[0] as AppOutletApplication

			assertNull(appOutletApp.icon)
			assertNull(appOutletApp.downloadUrl)

			return@answers appOutletApp.toMono()
		}

		assertTrue(getFlathubSynchronizer().synchronize().block() == true)
	}

	@Test
	fun `Should not synchronize if property is false `() {
		every { synchronizationPropertiesMock.flathub.enabled }.returns(false)
		assertFalse(getFlathubSynchronizer().synchronize().block() == true)
	}
}
