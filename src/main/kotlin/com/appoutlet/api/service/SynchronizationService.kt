package com.appoutlet.api.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SynchronizationService(
    private val flathubSynchronizer: FlathubSynchronizer,
    private val appImageHubSynchronizer: AppImageHubSynchronizer,
    private val snapStoreSynchronizer: SnapStoreSynchronizer,
    @Value("#{environment['appoutlet.synchronization.enable']}") private val synchronizationEnabled: Boolean
) {
	private val logger = LoggerFactory.getLogger(SynchronizationService::class.java)

	init {
	    if (!synchronizationEnabled) {
			logger.warn("Synchronization is not enabled for this instance")
		}
	}

	@Scheduled(cron = "#{environment['appoutlet.synchronization.cron']}")
	fun synchronize() {
		if (synchronizationEnabled) {
			startSynchronization()
		}
	}

	private fun startSynchronization() {
		logger.info("Starting synchronization")

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
