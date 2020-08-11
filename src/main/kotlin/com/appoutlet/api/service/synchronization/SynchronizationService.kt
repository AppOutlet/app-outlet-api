package com.appoutlet.api.service.synchronization

import com.appoutlet.api.model.ApplicationStore
import com.appoutlet.api.model.Synchronization
import com.appoutlet.api.repository.SynchronizationRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.Date

@Service
class SynchronizationService(
    private val flathubSynchronizer: FlathubSynchronizer,
    private val appImageHubSynchronizer: AppImageHubSynchronizer,
    private val snapStoreSynchronizer: SnapStoreSynchronizer,
    private val synchronizationProperties: SynchronizationProperties,
    private val synchronizationRepository: SynchronizationRepository
) {
	private val logger = LoggerFactory.getLogger(SynchronizationService::class.java)

	init {
		if (!synchronizationProperties.enabled) {
			logger.warn("Synchronization is not enabled for this instance")
		}
	}

	@Scheduled(cron = "#{environment['appoutlet.synchronization.cron']}")
	fun synchronize() {
		if (synchronizationProperties.enabled) {
			startSynchronization()
		}
	}

	private fun startSynchronization() {
		flathubSynchronizer.synchronize()
			.flatMap { createSynchronizationEntry(ApplicationStore.FLATHUB) }
			.doOnError { logger.info("Error on Flathub synchronization", it) }
			.subscribe { if (it) logger.info("Flathub synchronized successfully") }

		appImageHubSynchronizer.synchronize()
			.flatMap { createSynchronizationEntry(ApplicationStore.APP_IMAGE_HUB) }
			.doOnError { logger.error("Error on AppImageHub synchronization", it) }
			.subscribe { if (it) logger.info("AppImageHub synchronized successfully") }

		snapStoreSynchronizer.synchronize()
			.flatMap { createSynchronizationEntry(ApplicationStore.SNAP_STORE) }
			.doOnError { logger.error("Error on SnapStore synchronization", it) }
			.subscribe { if (it) logger.info("SnapStore synchronized successfully") }
	}

	private fun createSynchronizationEntry(store: ApplicationStore): Mono<Boolean> {
		val sync = Synchronization(date = Date(), store = store)
		return synchronizationRepository.save(sync).map { true }
	}
}
