package com.appoutlet.api.repository.appoutlet

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import reactor.core.publisher.Flux

class CustomApplicationRepositoryImpl : CustomApplicationRepository {
	override fun searchNameOrDescription(searchTerm: String): Flux<AppOutletApplication> {
		TODO("Not yet implemented")
	}
}
