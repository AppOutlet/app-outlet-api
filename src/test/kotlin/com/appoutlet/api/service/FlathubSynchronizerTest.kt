package com.appoutlet.api.service

import com.appoutlet.api.model.flathub.FlathubCategory
import com.appoutlet.api.model.flathub.FlathubScreenshot
import com.appoutlet.api.repository.AppOutletApplicationRepository
import com.appoutlet.api.repository.FlathubRepository
import com.appoutlet.api.repository.SynchronizationRepository
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Flathub synchronizer")
internal class FlathubSynchronizerTest {

	private val mockFlathubRepository = mockk<FlathubRepository>()
	private val mockAppOutletApplicationRepository = mockk<AppOutletApplicationRepository>()
	private val mockSynchronizationRepository = mockk<SynchronizationRepository>()
	private val flathubSynchronizer = FlathubSynchronizer(
		mockFlathubRepository,
		mockAppOutletApplicationRepository,
		mockSynchronizationRepository
	)

	@Test
	fun `Should synchronize `() {
		// TODO: Implement synchronization test
	}

	@Test
	fun `Should convert FlathubApplication to App Outlet application `() {
		// TODO: implement application conversion test
	}

	@Test
	fun `Should return a list of strings from a list of flathub categories `() {
		val categoryList = listOf(
			FlathubCategory("category1"),
			FlathubCategory("category2"),
			FlathubCategory("category3")
		)
		assertEquals(listOf("category1", "category2", "category3"), flathubSynchronizer.extractTags(categoryList))
		assertEquals(emptyList<String>(), flathubSynchronizer.extractScreenshots(emptyList()))
	}

	@Test
	fun `Extract screenshot url from flathub screenshot list `() {
		val screenshotList = listOf(
			FlathubScreenshot(thumbUrl = "", imgDesktopUrl = "image1", imgMobileUrl = ""),
			FlathubScreenshot(thumbUrl = "", imgDesktopUrl = "image2", imgMobileUrl = ""),
			FlathubScreenshot(thumbUrl = "", imgDesktopUrl = "image3", imgMobileUrl = "")
		)
		assertEquals(listOf("image1", "image2", "image3"), flathubSynchronizer.extractScreenshots(screenshotList))
		assertEquals(emptyList<String>(), flathubSynchronizer.extractScreenshots(emptyList()))
	}

	@Test
	fun `Should add flathub domain before a valid URI `() {
		assertEquals(
			"https://dl.flathub.org/something",
			flathubSynchronizer.addFlathubContentManagerDomain("/something")
		)

		assertNull(flathubSynchronizer.addFlathubContentManagerDomain("something"))
	}

	@Test
	fun `Verify if URI is valid `() {
		assertTrue(flathubSynchronizer.isFlatpakUriValid("/something"))
		assertFalse(flathubSynchronizer.isFlatpakUriValid("something"))
	}
}
