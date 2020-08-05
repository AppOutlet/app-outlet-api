package com.appoutlet.api.repository.appimagehub

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

internal class AppImageHubRepositoryTest {
	private val mockExchangeFunction = mockk<ExchangeFunction>()
	private lateinit var appImageHubRepository: AppImageHubRepository

	@BeforeEach
	fun setup() {
		val webClient = WebClient.builder()
			.exchangeFunction(mockExchangeFunction)
			.build()

		appImageHubRepository = AppImageHubRepository(AppImageHubWebClient(webClient))
	}

	@Test
	fun getApps() {
		val mockResponse = """
			{
				"version": 1,
				"home_page_url": "https://appimage.github.io/",
				"feed_url": "https://appimage.github.io/feed.json",
				"description": "AppImage applications for Linux without installation",
				"icon": "https://appimage.github.io/apple-touch-icon.png",
				"favicon": "https://appimage.github.io/favicon.ico",
				"expired": false,
				"items": [
						{
						"name": "AKASHA",
						"description": "Akasha platform",
						"categories": [
							"Network"
						],
						"authors": [
							{
								"name": "AkashaProject",
								"url": "https://github.com/AkashaProject"
							}
						],
						"license": null,
						"links": [
							{
								"type": "GitHub",
								"url": "AkashaProject/Community"
							},
							{
								"type": "Download",
								"url": "https://github.com/AkashaProject/Community/releases"
							}
						],
						"icons": [
							"AKASHA/icons/128x128/akasha.png"
						],
						"screenshots": [
							"AKASHA/screenshot.png"
						]
					},
					{
						"name": "ANT_Downloader",
						"categories": [
							"Utility"
						],
						"authors": [
							{
								"name": "anatasluo",
								"url": "https://github.com/anatasluo"
							}
						],
						"license": "MPL-2.0",
						"links": [
							{
								"type": "GitHub",
								"url": "anatasluo/ant"
							},
							{
								"type": "Download",
								"url": "https://github.com/anatasluo/ant/releases"
							}
						],
						"icons": [
							"ANT_Downloader/icons/989x652/ant-downloader.png"
						],
						"screenshots": [
							"ANT_Downloader/screenshot.png"
						]
					},
					{
						"name": "APK_Editor_Studio",
						"description": "Edit APK resources",
						"categories": [
							"Utility"
						],
						"authors": [
							{
								"name": "kefir500",
								"url": "https://github.com/kefir500"
							}
						],
						"license": null,
						"links": [
							{
								"type": "GitHub",
								"url": "kefir500/apk-editor-studio"
							},
							{
								"type": "Download",
								"url": "https://github.com/kefir500/apk-editor-studio/releases"
							}
						],
						"icons": [
							"APK_Editor_Studio/icons/128x128/apk-editor-studio.png"
						],
						"screenshots": [
							"APK_Editor_Studio/screenshot.png"
						]
					}
				]
			}
		""".trimIndent()

		val clientResponse = ClientResponse.create(HttpStatus.OK)
			.header("content-type", "application/json")
			.body(mockResponse)
			.build()

		every { mockExchangeFunction.exchange(any()) }.returns(Mono.just(clientResponse))

		val applications = appImageHubRepository.getApps().buffer().toMono().block()
		assertEquals(3, applications?.size)
	}
}
