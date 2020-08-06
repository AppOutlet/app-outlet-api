package com.appoutlet.api.service.synchronization

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.model.snapstore.SnapStoreApplication
import com.appoutlet.api.repository.AppOutletApplicationRepository
import com.appoutlet.api.repository.snapstore.SnapStoreRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import reactor.core.publisher.Flux
import java.util.Date

internal class SnapStoreSynchronizerTest {
	private lateinit var snapStoreSynchronizer: SnapStoreSynchronizer
	private val snapStoreRepositoryMock = mockk<SnapStoreRepository>()
	private val appOutletApplicationRepositoryMock = mockk<AppOutletApplicationRepository>()
	private val synchronizationPropertiesMock = mockk<SynchronizationProperties>()

	@BeforeEach
	fun setup() {
		every { synchronizationPropertiesMock.snapStore.enabled } returns false
		snapStoreSynchronizer = SnapStoreSynchronizer(
			snapStoreRepositoryMock,
			appOutletApplicationRepositoryMock,
			synchronizationPropertiesMock
		)
	}

    @Test
    fun `Should not synchronize if property is false `() {
		every { synchronizationPropertiesMock.snapStore.enabled } returns false
		assertFalse(snapStoreSynchronizer.synchronize().block() == true)
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
		val appOutletAppliationMock = mockk<AppOutletApplication>()
		every { synchronizationPropertiesMock.snapStore.enabled } returns true
		every { snapStoreRepositoryMock.getApps() }.returns(Flux.fromIterable(apps))
		every { appOutletApplicationRepositoryMock.save<AppOutletApplication>(any()) } returns appOutletAppliationMock

		assertTrue(snapStoreSynchronizer.synchronize().block() == true)
	}
}
