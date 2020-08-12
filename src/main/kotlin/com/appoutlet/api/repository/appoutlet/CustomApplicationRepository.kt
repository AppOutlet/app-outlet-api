package com.appoutlet.api.repository.appoutlet

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import reactor.core.publisher.Flux

interface CustomApplicationRepository {
	fun searchNameOrDescription(searchTerm: String): Flux<AppOutletApplication>
}
