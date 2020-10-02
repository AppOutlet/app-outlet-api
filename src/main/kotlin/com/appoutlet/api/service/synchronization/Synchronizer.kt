package com.appoutlet.api.service.synchronization

import reactor.core.publisher.Mono

interface Synchronizer {
	fun synchronize(): Mono<Boolean>
}
