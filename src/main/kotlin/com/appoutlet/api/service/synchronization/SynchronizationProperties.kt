package com.appoutlet.api.service.synchronization

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "appoutlet.synchronization")
class SynchronizationProperties(
    val enabled: Boolean,
    val flathub: FlathubSynchronizationProperties
) {
	class FlathubSynchronizationProperties(
	    val enabled: Boolean
	)
}
