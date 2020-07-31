package com.appoutlet.api.service

import com.appoutlet.api.repository.SnapStoreRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class SnapStoreSynchronizer(
	private val snapStoreRepository: SnapStoreRepository
): Synchronizer {
	override fun synchronize(): Mono<Boolean> {
		return snapStoreRepository.getApps()
			.buffer()
			.map { true }
			.toMono()
	}
}
