package com.appoutlet.api.service.synchronization

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.model.snapstore.SnapStoreApplication
import com.appoutlet.api.repository.appoutlet.ApplicationRepository
import com.appoutlet.api.repository.snapstore.SnapStoreRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Date

internal class SnapStoreSynchronizerTest {
	private val snapStoreRepositoryMock = mockk<SnapStoreRepository>()
	private val appOutletApplicationRepositoryMock = mockk<ApplicationRepository>()
	private val synchronizationPropertiesMock = mockk<SynchronizationProperties>()

	private fun getSnapStoreSynchronizer() = SnapStoreSynchronizer(
		snapStoreRepositoryMock,
		appOutletApplicationRepositoryMock,
		synchronizationPropertiesMock
	)

    @Test
    fun `Should not synchronize if property is false `() {
		every { synchronizationPropertiesMock.snapStore.enabled } returns false
		assertFalse(getSnapStoreSynchronizer().synchronize().block() == true)
    }

	@Test
	fun `Should synchronize if property is true `() {
		val apps = listOf(
			SnapStoreApplication(
				snap_id = "snapId",
				name = "Application name",
				summary = "Application Summary",
				description = "Application description",
				developer_name = "Developer name",
				license = "MIT",
				website = "http://app-outlet.github.io",
				support_url = "https://github.com/app-outlet/app-outlet/issues",
				icon_url = "https://path.to/icon.png",
				download_url = "https://path.to/download.snap",
				version = "1.0",
				last_updated = Date(),
				date_published = Date(),
				screenshot_urls = listOf("https://path.to/screenshot.pnh"),
				package_name = "app-outlet",
				confinement = "strict"
			)
		)
		val appOutletApplicationMock = mockk<AppOutletApplication>()
		every { synchronizationPropertiesMock.snapStore.enabled } returns true
		every { snapStoreRepositoryMock.getApps() }.returns(Flux.fromIterable(apps))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) }.returns(
			Mono.just(appOutletApplicationMock)
		)

		assertTrue(getSnapStoreSynchronizer().synchronize().block() == true)
	}
}
