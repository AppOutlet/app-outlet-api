package com.appoutlet.api.service

import reactor.core.publisher.Mono

interface Synchronizer {
	fun synchronize(): Mono<Boolean>
}
