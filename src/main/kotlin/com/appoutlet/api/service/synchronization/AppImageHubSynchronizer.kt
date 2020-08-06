package com.appoutlet.api.service.synchronization

import com.appoutlet.api.model.ApplicationPackageType
import com.appoutlet.api.model.ApplicationStore
import com.appoutlet.api.model.appimagehub.AppImageHubApplication
import com.appoutlet.api.model.appimagehub.AppImageHubLink
import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.repository.AppOutletApplicationRepository
import com.appoutlet.api.repository.appimagehub.AppImageHubRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.Date

@Service
class AppImageHubSynchronizer(
    private val appImageHubRepository: AppImageHubRepository,
    private val appOutletApplicationRepository: AppOutletApplicationRepository,
    @Value("#{environment['appoutlet.synchronization.app-image-hub.enable']}") private val syncEnabled: Boolean
) : Synchronizer {
	private val logger = LoggerFactory.getLogger(AppImageHubSynchronizer::class.java)

	init {
		if (!syncEnabled) {
			logger.warn("Synchronization disabled for AppImageHub")
		}
	}

	override fun synchronize(): Mono<Boolean> {
		return if (syncEnabled) {
			startSynchronization()
		} else {
			Mono.just(false)
		}
	}

	private fun startSynchronization() = appImageHubRepository.getApps().toFlux()
		.map(this::convertAppImageApplicationToAppOutletApplication)
		.map(this::saveApplication)
		.buffer()
		.toMono()
		.map { true }

	private fun saveApplication(application: AppOutletApplication): AppOutletApplication {
		return appOutletApplicationRepository.save(application)
	}

	private fun convertAppImageApplicationToAppOutletApplication(
	    appImageHubApplication: AppImageHubApplication
	): AppOutletApplication {
		return AppOutletApplication(
			id = getId(appImageHubApplication),
			name = appImageHubApplication.name,
			summary = appImageHubApplication.description,
			description = appImageHubApplication.description,
			developer = getDeveloper(appImageHubApplication),
			license = appImageHubApplication.license,
			homepage = getHomepage(appImageHubApplication),
			bugtrackerUrl = getBugtrackerUrl(appImageHubApplication),
			donationUrl = null,
			icon = getIcon(appImageHubApplication),
			downloadUrl = null,
			version = null,
			lastReleaseDate = Date(),
			creationDate = null,
			tags = appImageHubApplication.categories,
			screenshots = getScreenshots(appImageHubApplication),
			store = ApplicationStore.APP_IMAGE_HUB,
			packageType = ApplicationPackageType.APP_IMAGE
		)
	}

	private fun getId(appImageHubApplication: AppImageHubApplication): String {
		val author = getDeveloper(appImageHubApplication)
		return "$author.${appImageHubApplication.name}"
	}

	private fun getDeveloper(appImageHubApplication: AppImageHubApplication): String {
		return appImageHubApplication.authors?.get(0)?.name ?: "unknown"
	}

	private fun getHomepage(appImageHubApplication: AppImageHubApplication): String? {
		val uri = appImageHubApplication.appImageHubLinks?.find { it.type == AppImageHubLink.Type.GITHUB }?.url
		return uri?.let { "https://github.com/$it" }
	}

	private fun getBugtrackerUrl(appImageHubApplication: AppImageHubApplication): String? {
		val githubLink = getHomepage(appImageHubApplication)
		return githubLink?.let { "$githubLink/issues" }
	}

	private fun getIcon(appImageHubApplication: AppImageHubApplication): String? {
		val iconUri = appImageHubApplication.icons?.get(0)
		return iconUri?.let { "https://gitcdn.xyz/repo/AppImage/appimage.github.io/master/database/$it" }
	}

	private fun getScreenshots(appImageHubApplication: AppImageHubApplication): List<String> {
		val screenshots = mutableListOf<String>()
		appImageHubApplication.screenshots?.forEach { imageUri ->
			screenshots.add("https://appimage.github.io/database/$imageUri")
		}
		return screenshots
	}
}
