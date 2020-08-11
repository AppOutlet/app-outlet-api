package com.appoutlet.api.service.synchronization

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

internal class SynchronizationServiceTest {
	private lateinit var synchronizationService: SynchronizationService
	private val mockFlathubSynchronizer = mockk<FlathubSynchronizer>()
	private val mockAppImageHubSynchronizer = mockk<AppImageHubSynchronizer>()
	private val mockSnapStoreSynchronizer = mockk<SnapStoreSynchronizer>()
	private val mockSynchronizationProperties = mockk<SynchronizationProperties>()

	@BeforeEach
	fun setup() {
		every { mockSynchronizationProperties.enabled }.returns(false)
		synchronizationService = SynchronizationService(
			mockFlathubSynchronizer,
			mockAppImageHubSynchronizer,
			mockSnapStoreSynchronizer,
			mockSynchronizationProperties
		)
	}

	@Test
	fun synchronize() {
		every { mockSynchronizationProperties.enabled }.returns(true)
		every { mockFlathubSynchronizer.synchronize() }.returns(Mono.just(true))
		every { mockAppImageHubSynchronizer.synchronize() }.returns(Mono.just(true))
		every { mockSnapStoreSynchronizer.synchronize() }.returns(Mono.just(true))

		synchronizationService.synchronize()

		verify(exactly = 1) { mockFlathubSynchronizer.synchronize() }
		verify(exactly = 1) { mockAppImageHubSynchronizer.synchronize() }
		verify(exactly = 1) { mockSnapStoreSynchronizer.synchronize() }
	}

	@Test
	fun `Should not synchronize if property is false `() {
		every { mockSynchronizationProperties.enabled }.returns(false)
		every { mockFlathubSynchronizer.synchronize() }.returns(Mono.just(true))
		every { mockAppImageHubSynchronizer.synchronize() }.returns(Mono.just(true))
		every { mockSnapStoreSynchronizer.synchronize() }.returns(Mono.just(true))

		synchronizationService.synchronize()

		verify(exactly = 0) { mockFlathubSynchronizer.synchronize() }
		verify(exactly = 0) { mockAppImageHubSynchronizer.synchronize() }
		verify(exactly = 0) { mockSnapStoreSynchronizer.synchronize() }
	}

	@Test
	fun `Synchronize with synchronizer returning error `() {
		every { mockSynchronizationProperties.enabled }.returns(true)
		every { mockFlathubSynchronizer.synchronize() }.returns(Mono.error(RuntimeException()))
		every { mockAppImageHubSynchronizer.synchronize() }.returns(Mono.error(RuntimeException()))
		every { mockSnapStoreSynchronizer.synchronize() }.returns(Mono.error(RuntimeException()))

		synchronizationService.synchronize()

		verify(exactly = 1) { mockFlathubSynchronizer.synchronize() }
		verify(exactly = 1) { mockAppImageHubSynchronizer.synchronize() }
		verify(exactly = 1) { mockSnapStoreSynchronizer.synchronize() }
	}

	@Test
	fun `Synchronize with Synchronizer returning false `() {
		every { mockSynchronizationProperties.enabled }.returns(true)
		every { mockFlathubSynchronizer.synchronize() }.returns(false.toMono())
		every { mockAppImageHubSynchronizer.synchronize() }.returns(false.toMono())
		every { mockSnapStoreSynchronizer.synchronize() }.returns(false.toMono())

		synchronizationService.synchronize()

		verify(exactly = 1) { mockFlathubSynchronizer.synchronize() }
		verify(exactly = 1) { mockAppImageHubSynchronizer.synchronize() }
		verify(exactly = 1) { mockSnapStoreSynchronizer.synchronize() }
	}
}
