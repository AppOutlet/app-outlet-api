package com.appoutlet.api.service.synchronization

import com.appoutlet.api.model.ApplicationPackageType
import com.appoutlet.api.model.ApplicationStore
import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.model.flathub.FlathubApplication
import com.appoutlet.api.model.flathub.FlathubApplicationDetails
import com.appoutlet.api.model.flathub.FlathubCategory
import com.appoutlet.api.model.flathub.FlathubScreenshot
import com.appoutlet.api.repository.AppOutletApplicationRepository
import com.appoutlet.api.repository.flathub.FlathubRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Date

@DisplayName("Flathub synchronizer")
internal class FlathubSynchronizerTest {

	private val mockFlathubRepository = mockk<FlathubRepository>()
	private val mockAppOutletApplicationRepository = mockk<AppOutletApplicationRepository>()
	private val mockSynchronizationProperties = mockk<SynchronizationProperties>()
	private lateinit var flathubSynchronizer: FlathubSynchronizer

	@BeforeEach
	fun setup() {
		every { mockSynchronizationProperties.flathub.enabled }.returns(false)
		flathubSynchronizer = FlathubSynchronizer(
			mockFlathubRepository,
			mockAppOutletApplicationRepository,
			mockSynchronizationProperties
		)
	}

	@Test
	fun `Should synchronize `() {
		val flatpakApp1 = "flatpak.app.1"
		val flatpakApp2 = "flatpak.app.2"

		val applications = listOf(
			FlathubApplication(flatpakAppId = flatpakApp1),
			FlathubApplication(flatpakAppId = flatpakApp2)
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
			screenshots = listOf(FlathubScreenshot(imgDesktopUrl = "/screenshot1.png"))
		)

		val applicationDetails2 = FlathubApplicationDetails(
			flatpakAppId = flatpakApp2,
			name = "Application name",
			summary = "Application summary",
			description = "Application description",
			developerName = "Developer",
			projectLicense = "MIT",
			homepageUrl = "https://application.com",
			bugtrackerUrl = "https://application.com/issues",
			donationUrl = "https://application.com/donate",
			iconDesktopUrl = "invalid icon",
			downloadFlatpakRefUrl = "/application.flatpakref",
			currentReleaseVersion = "1.0",
			currentReleaseDate = Date(),
			inStoreSinceDate = Date(),
			categories = listOf(FlathubCategory("Category1")),
			screenshots = listOf(FlathubScreenshot(imgDesktopUrl = "/screenshot1.png"))
		)

		val appOutletApplication = AppOutletApplication(
			id = "1",
			name = "ApplicationName",
			summary = "Application summary",
			description = "Application description",
			developer = "Developer",
			license = "MIT",
			store = ApplicationStore.FLATHUB,
			packageType = ApplicationPackageType.FLATPAK
		)

		every { mockSynchronizationProperties.flathub.enabled }.returns(true)
		every { mockFlathubRepository.getApps() }.returns(Flux.fromIterable(applications))
		every { mockFlathubRepository.getApplicationDetails(flatpakApp1) }.returns(Mono.just(applicationDetails))
		every { mockFlathubRepository.getApplicationDetails(flatpakApp2) }.returns(Mono.just(applicationDetails2))
		every { mockAppOutletApplicationRepository.save<AppOutletApplication>(any()) }.returns(Mono.just(appOutletApplication))

		assertTrue(flathubSynchronizer.synchronize().block() == true)
	}

	@Test
	fun `Should not synchronize if property is false `() {
		every { mockSynchronizationProperties.flathub.enabled }.returns(false)
		assertFalse(flathubSynchronizer.synchronize().block() == true)
	}
}
