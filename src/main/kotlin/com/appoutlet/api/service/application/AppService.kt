package com.appoutlet.api.service.application

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.repository.AppOutletApplicationRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AppService(
	private val appOutletApplicationRepository: AppOutletApplicationRepository
) {
	fun findAll() = appOutletApplicationRepository.findAll()

	fun registerVisualization(appId: String): Mono<AppOutletApplication> {
		return appOutletApplicationRepository.findById(appId)
			.map(this::incrementApplicationViewCount)
			.flatMap(this::save)
	}

	private fun incrementApplicationViewCount(application: AppOutletApplication) : AppOutletApplication {
		return application.copy(viewCount = (application.viewCount ?: 0) + 1)
	}

	fun save(appOutletApplication: AppOutletApplication) = appOutletApplicationRepository.save(appOutletApplication)
}
