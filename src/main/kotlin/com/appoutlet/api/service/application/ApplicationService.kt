package com.appoutlet.api.service.application

import com.appoutlet.api.exception.ApplicationNotFoundException
import com.appoutlet.api.model.appoutlet.AppOutletApplication
import com.appoutlet.api.repository.appoutlet.ApplicationRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository
) {
	fun findAll() = applicationRepository.findAll()

	fun registerVisualization(appId: String): Mono<AppOutletApplication> {
		return applicationRepository.findById(appId)
			.switchIfEmpty { Mono.error(ApplicationNotFoundException("Application with id $appId was not found")) }
			.map(this::incrementApplicationViewCount)
			.flatMap(this::save)
	}

	private fun incrementApplicationViewCount(application: AppOutletApplication): AppOutletApplication {
		return application.copy(viewCount = (application.viewCount ?: 0) + 1)
	}

	fun save(appOutletApplication: AppOutletApplication) = applicationRepository.save(appOutletApplication)

	fun search(searchTerm: String, page: Int?, size: Int?): Flux<AppOutletApplication> {
		val pageable = PageRequest.of(page ?: 0, size ?: DEFAULT_PAGE_SIZE)
		return applicationRepository.searchNameOrDescription(searchTerm, pageable)
	}

	companion object {
		const val DEFAULT_PAGE_SIZE = 15
	}
}
