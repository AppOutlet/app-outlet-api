package com.appoutlet.api.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SynchronizationService(
    private val flathubSynchronizer: FlathubSynchronizer,
    private val appImageHubSynchronizer: AppImageHubSynchronizer,
    private val snapStoreSynchronizer: SnapStoreSynchronizer
) {
	private val logger = LoggerFactory.getLogger(SynchronizationService::class.java)

	@Scheduled(cron = "#{environment['appoutlet.synchronization.cron']}")
	fun startSynchronization() {
		// TODO: Before start synchronization we have to verify if this feature is enabled. This flag should be on properties file
 		flathubSynchronizer.synchronize()
 			.doOnError { logger.info("Error on Flathub synchronization") }
 			.subscribe { logger.info("Flathub synchronized successfully") }

		appImageHubSynchronizer.synchronize()
			.doOnError { logger.error("Error on AppImageHub synchronization", it) }
			.subscribe { logger.info("AppImageHub synchronized successfully") }

		snapStoreSynchronizer.synchronize()
			.doOnError { logger.error("Error on SnapStore synchronization", it) }
			.subscribe { logger.info("SnapStore synchronized successfully") }
	}
}
