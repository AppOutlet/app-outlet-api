package com.appoutlet.api.service.synchronization

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "appoutlet.synchronization")
class SynchronizationProperties(
    val enabled: Boolean,
    val flathub: FlathubSynchronizationProperties,
	val appImageHub: AppImageHubSynchronizationProperties,
	val snapStore: SnapStoreSynchronizationProperties
) {
	class FlathubSynchronizationProperties(
	    val enabled: Boolean
	)

	class AppImageHubSynchronizationProperties(
		val enabled: Boolean
	)

	class SnapStoreSynchronizationProperties(
		val enabled: Boolean
	)
}
