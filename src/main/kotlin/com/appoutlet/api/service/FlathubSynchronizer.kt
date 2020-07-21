package com.appoutlet.api.service

import com.appoutlet.api.model.AppOutletApplication
import com.appoutlet.api.model.ApplicationPackageType
import com.appoutlet.api.model.ApplicationStore
import com.appoutlet.api.model.FlathubApplicationDetails
import com.appoutlet.api.model.FlathubCategory
import com.appoutlet.api.model.FlathubScreenshot
import com.appoutlet.api.model.Synchronization
import com.appoutlet.api.repository.AppOutletApplicationRepository
import com.appoutlet.api.repository.FlathubRepository
import com.appoutlet.api.repository.SynchronizationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.Date

@Service
class FlathubSynchronizer(
    private val flathubRepository: FlathubRepository,
    private val appOutletApplicationRepository: AppOutletApplicationRepository,
    private val synchronizationRepository: SynchronizationRepository
) : Synchronizer {
	private val logger = LoggerFactory.getLogger(FlathubSynchronizer::class.java)

	override fun synchronize(): Mono<Boolean> {
		return flathubRepository.getApps()
			.flatMap { flathubRepository.getApplicationDetails(it.flatpakAppId) }
			.map(this::convertFlathubApplicationToAppOutletApplication)
			.map { appOutletApplicationRepository.save(it) }
			.buffer()
			.toMono()
			.flatMap { createSynchronizationEntry() }
	}

	fun createSynchronizationEntry() = Mono.create<Boolean> {
		val sync = Synchronization(
			date = Date(),
			store = ApplicationStore.FLATHUB
		)
		synchronizationRepository.save(sync)
		it.success(true)
	}

	fun convertFlathubApplicationToAppOutletApplication(
	    flathubApplication: FlathubApplicationDetails
	): AppOutletApplication {
		return AppOutletApplication(
			id = flathubApplication.flatpakAppId,
			name = flathubApplication.name,
			summary = flathubApplication.summary,
			description = flathubApplication.description,
			developer = flathubApplication.developerName,
			license = flathubApplication.projectLicense,
			homepage = flathubApplication.homepageUrl,
			bugtrackerUrl = flathubApplication.bugtrackerUrl,
			donationUrl = flathubApplication.donationUrl,
			icon = addFlathubContentManagerDomain(flathubApplication.iconDesktopUrl),
			downloadUrl = addFlathubContentManagerDomain(flathubApplication.downloadFlatpakRefUrl),
			version = flathubApplication.currentReleaseVersion,
			lastReleaseDate = flathubApplication.currentReleaseDate,
			creationDate = flathubApplication.inStoreSinceDate,
			tags = extractTags(flathubApplication.categories),
			screenshots = extractScreenshots(flathubApplication.screenshots),
			store = ApplicationStore.FLATHUB,
			packageType = ApplicationPackageType.FLATPAK
		)
	}

	fun extractTags(categories: List<FlathubCategory>): List<String> {
		val result = mutableListOf<String>()
		categories.forEach { category ->
			result.add(category.name)
		}
		return result
	}

	fun extractScreenshots(screenshots: List<FlathubScreenshot>): List<String> {
		val result = mutableListOf<String>()
		screenshots.forEach { screenshot ->
			result.add(screenshot.imgDesktopUrl)
		}
		return result
	}

	fun addFlathubContentManagerDomain(uri: String): String? {
		return if (isFlatpakUriValid(uri)) {
			"$FLATHUB_CONTENT_MANAGER_DOMAIN$uri"
		} else {
			logger.warn("Invalid URI {}", uri)
			null
		}
	}

	fun isFlatpakUriValid(uri: String): Boolean {
		return uri.isNotBlank() &&
			uri.startsWith("/")
	}

	companion object {
		private const val FLATHUB_CONTENT_MANAGER_DOMAIN = "https://dl.flathub.org"
	}
}
