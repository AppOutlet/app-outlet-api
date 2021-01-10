package com.appoutlet.api.repository.appoutlet

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ApplicationRepository : ReactiveMongoRepository<AppOutletApplication, String> {
	@Query(
		"""{ ${'$'}or : [
			{ name: { ${'$'}regex: '?0', ${'$'}options: 'i' }},
			{ summary: { ${'$'}regex: '?0', ${'$'}options: 'i' }},
			{ description: { ${'$'}regex: '?0', ${'$'}options: 'i' }}
		]}"""
	)
	fun searchNameOrDescription(searchTerm: String, pageable: Pageable): Flux<AppOutletApplication>
}
