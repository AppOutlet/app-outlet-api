package com.appoutlet.api.service

import com.appoutlet.api.model.AppOutletApplication
import com.appoutlet.api.model.ApplicationPackageType
import com.appoutlet.api.model.ApplicationStore
import com.appoutlet.api.model.FlathubApplicationDetails
import com.appoutlet.api.model.FlathubCategory
import com.appoutlet.api.model.FlathubScreenshot
import com.appoutlet.api.repository.FlathubRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FlathubSynchronizer(
    private val flathubRepository: FlathubRepository
) : Synchronizer {
	private val logger = LoggerFactory.getLogger(FlathubSynchronizer::class.java)

	init {
		// TODO: Remove this call
	    synchronize()
	}

	override fun synchronize(): Mono<Boolean> {
		var counter = 0
		flathubRepository.getApps()
			.flatMap { flathubRepository.getApplicationDetails(it.flatpakAppId) }
			.map(this::convertFlathubApplicationToAppOutletApplication)

		return Mono.just(true)
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
