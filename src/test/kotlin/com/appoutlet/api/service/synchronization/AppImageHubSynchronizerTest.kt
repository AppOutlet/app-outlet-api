package com.appoutlet.api.service.synchronization

import com.appoutlet.api.model.appimagehub.AppImageHubApplication
import com.appoutlet.api.model.appimagehub.AppImageHubAuthor
import com.appoutlet.api.model.appimagehub.AppImageHubLink
import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.repository.appimagehub.AppImageHubRepository
import com.appoutlet.api.repository.appoutlet.ApplicationRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono

internal class AppImageHubSynchronizerTest {
	private val appImageHubRepositoryMock = mockk<AppImageHubRepository>() // TODO: rename all mocks to this pattern
	private val appOutletApplicationRepositoryMock = mockk<ApplicationRepository>()
	private val synchronizationPropertiesMock = mockk<SynchronizationProperties>()

	private fun getAppImageHubSynchronizer(): AppImageHubSynchronizer {
		return AppImageHubSynchronizer(
			appImageHubRepositoryMock,
			appOutletApplicationRepositoryMock,
			synchronizationPropertiesMock
		)
	}

	@Test
	fun `Should not synchronize if the property is false `() {
		every { synchronizationPropertiesMock.appImageHub.enabled } returns false
		assertFalse(getAppImageHubSynchronizer().synchronize().block() == true)
	}

	@Test
	fun `Should synchronize app image applications `() {
		val applications = listOf(
			AppImageHubApplication(
				authors = listOf(
					AppImageHubAuthor("author 1"),
					AppImageHubAuthor("author 2")
				),
				name = "app1",
				description = "Application description",
				license = "MIT",
				links = listOf(AppImageHubLink(AppImageHubLink.Type.GITHUB, "app-outlet/app-outlet")),
				icons = listOf("path/icon.png"),
				screenshots = listOf("path/screenshot.png"),
				categories = listOf("Category")
			)
		)

		every { synchronizationPropertiesMock.appImageHub.enabled } returns true
		every { appImageHubRepositoryMock.getApps() }.returns(Flux.fromIterable(applications))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.answers {
			val appToSave = it.invocation.args[0] as AppOutletApplication

			assertEquals("author 1", appToSave.developer)
			assertEquals("author 1.app1", appToSave.id)
			assertEquals("https://github.com/app-outlet/app-outlet", appToSave.homepage)
			assertEquals("https://github.com/app-outlet/app-outlet/issues", appToSave.bugtrackerUrl)
			assertEquals(
				"https://gitcdn.xyz/repo/AppImage/appimage.github.io/master/database/path/icon.png",
				appToSave.icon
			)
			assertEquals("https://appimage.github.io/database/path/screenshot.png", appToSave.screenshots?.get(0))

			return@answers appToSave.toMono()
		}
		assertTrue(getAppImageHubSynchronizer().synchronize().block() == true)
	}

	@Test
	fun `Should set developer as 'unknown' if there is no authors `() {
		val applications = listOf(
			AppImageHubApplication(
				authors = null,
				name = "app1"
			),
			AppImageHubApplication(
				authors = emptyList(),
				name = "app2"
			)
		)

		every { synchronizationPropertiesMock.appImageHub.enabled } returns true
		every { appImageHubRepositoryMock.getApps() }.returns(Flux.fromIterable(applications))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.answers {
			val appToSave = it.invocation.args[0] as AppOutletApplication
			assertEquals("unknown", appToSave.developer)
			return@answers appToSave.toMono()
		}
		assertTrue(getAppImageHubSynchronizer().synchronize().block() == true)
	}

	@Test
	fun `Should set the github link as homepage `() {
		val applications = listOf(
			AppImageHubApplication(
				name = "app1",
				links = listOf(
					AppImageHubLink(AppImageHubLink.Type.DOWNLOAD, "/download"),
					AppImageHubLink(AppImageHubLink.Type.GITHUB, "user/repo")
				)
			)
		)

		every { synchronizationPropertiesMock.appImageHub.enabled } returns true
		every { appImageHubRepositoryMock.getApps() }.returns(Flux.fromIterable(applications))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.answers {
			val appToSave = it.invocation.args[0] as AppOutletApplication

			assertEquals("https://github.com/user/repo", appToSave.homepage)

			return@answers appToSave.toMono()
		}
		assertTrue(getAppImageHubSynchronizer().synchronize().block() == true)
	}

	@Test
	fun `Should set homepage as null if there's no GITHUB link`() {
		val applications = listOf(
			AppImageHubApplication(
				name = "app1",
				links = null
			),
			AppImageHubApplication(
				name = "app2",
				links = emptyList()
			),
			AppImageHubApplication(
				name = "app3",
				links = listOf(
					AppImageHubLink(AppImageHubLink.Type.INSTALL, "/install")
				)
			),
			AppImageHubApplication(
				name = "app4",
				links = listOf(
					AppImageHubLink(AppImageHubLink.Type.GITHUB, null)
				)
			)
		)

		every { synchronizationPropertiesMock.appImageHub.enabled } returns true
		every { appImageHubRepositoryMock.getApps() }.returns(Flux.fromIterable(applications))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.answers {
			val appToSave = it.invocation.args[0] as AppOutletApplication

			assertNull(appToSave.homepage)

			return@answers appToSave.toMono()
		}
		assertTrue(getAppImageHubSynchronizer().synchronize().block() == true)
	}
}
