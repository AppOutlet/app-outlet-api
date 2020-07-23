package com.appoutlet.api.service

import com.appoutlet.api.repository.AppImageHubRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Service
class AppImageHubSynchronizer(
	private val appImageHubRepository: AppImageHubRepository
) : Synchronizer{
	override fun synchronize(): Mono<Boolean> {
		return appImageHubRepository.getApps().toFlux()
			.flatMap { Flux.fromIterable(it.items) }
			.buffer()
			.toMono()
			.map { true }
	}
}
