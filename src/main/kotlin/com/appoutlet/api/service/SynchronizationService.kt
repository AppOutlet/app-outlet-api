package com.appoutlet.api.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SynchronizationService(
    private val flathubSynchronizer: FlathubSynchronizer
) {
	private val logger = LoggerFactory.getLogger(SynchronizationService::class.java)

	@Scheduled(cron = "#{environment['appoutlet.synchronization.cron']}")
	fun startSynchronization() {
		flathubSynchronizer.synchronize()
			.doOnError { logger.info("Error on Flathub synchronization") }
			.subscribe { logger.info("Flathub synchronized successfully") }
	}
}
