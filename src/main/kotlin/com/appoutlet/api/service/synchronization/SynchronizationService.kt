package com.appoutlet.api.service.synchronization

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SynchronizationService(
	private val flathubSynchronizer: FlathubSynchronizer,
	private val appImageHubSynchronizer: AppImageHubSynchronizer,
	private val snapStoreSynchronizer: SnapStoreSynchronizer,
	private val synchronizationProperties: SynchronizationProperties
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
			.doOnError { logger.info("Error on Flathub synchronization", it) }
			.subscribe { if (it) logger.info("Flathub synchronized successfully") }

		appImageHubSynchronizer.synchronize()
			.doOnError { logger.error("Error on AppImageHub synchronization", it) }
			.subscribe { if (it) logger.info("AppImageHub synchronized successfully") }

		snapStoreSynchronizer.synchronize()
			.doOnError { logger.error("Error on SnapStore synchronization", it) }
			.subscribe { if (it) logger.info("SnapStore synchronized successfully") }
	}
}
