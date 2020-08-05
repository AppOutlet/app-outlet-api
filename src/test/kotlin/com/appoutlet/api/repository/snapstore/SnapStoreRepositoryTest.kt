package com.appoutlet.api.repository.snapstore

import com.appoutlet.api.repository.appimagehub.AppImageHubRepository
import com.appoutlet.api.repository.appimagehub.AppImageHubWebClient
import com.appoutlet.api.util.clientResponseFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.kotlin.core.publisher.toMono

class SnapStoreRepositoryTest {
	private val mockExchangeFunction = mockk<ExchangeFunction>()
	private lateinit var repository: SnapStoreRepository

	@BeforeEach
	fun setup() {
		val webClient = WebClient.builder()
			.exchangeFunction(mockExchangeFunction)
			.build()

		repository = SnapStoreRepository(SnapStoreWebClient(webClient))
	}

	@Test
	fun getApps() {
		val mockResponse = """
				{
					"_embedded": {
						"clickindex:package": [
							{
								"aliases": null,
								"anon_download_url": "https://api.snapcraft.io/api/v1/snaps/download/6L5ibHqUVmK8sWxSTJaMlwcr23dD2ct8_1.snap",
								"apps": [
									"hello",
									"bash"
								],
								"architecture": [
									"amd64"
								],
								"binary_filesize": 3051520,
								"channel": "stable",
								"common_ids": [],
								"confinement": "strict",
								"contact": "",
								"content": "application",
								"date_published": "2019-12-18T14:28:50.617683Z",
								"deltas": [],
								"description": "GNU hello prints a friendly greeting.",
								"developer_id": "xWKBfzJQocJhfYTVqtKHKgAu7l7ybR1G",
								"developer_name": "Navion MSRL",
								"developer_validation": "unproven",
								"download_sha3_384": "71de2a776aaad69cbf6c741b2d83c19c3372d3829a11171669f5e39b559c19591ca43f358c51df451c8408a8e7d6bde6",
								"download_sha512": "13cef71c14af4305334e7b3ad07aa105dbc284e78b59a22fe322eb46ae454ad4e72c6a35cdf9d60b0ab2f3ce85da53559c7409089ddf0aae3c81e25c3fe00390",
								"download_url": "https://api.snapcraft.io/api/v1/snaps/download/6L5ibHqUVmK8sWxSTJaMlwcr23dD2ct8_1.snap",
								"epoch": "0",
								"gated_snap_ids": [],
								"icon_url": "",
								"last_updated": "2019-12-18T14:28:37.529598+00:00",
								"license": "unset",
								"name": "hello-msrl.navion-msrl",
								"origin": "navion-msrl",
								"package_name": "hello-msrl",
								"prices": {},
								"private": false,
								"publisher": "Navion MSRL",
								"ratings_average": 0.0,
								"release": [
									"16"
								],
								"revision": 1,
								"screenshot_urls": [],
								"snap_id": "6L5ibHqUVmK8sWxSTJaMlwcr23dD2ct8",
								"summary": "GNU Hello, the \"hello world\" snap",
								"support_url": "",
								"title": "hello-msrl",
								"version": "2.10",
								"website": ""
							}
						]
					}
				}
		""".trimIndent()

		every { mockExchangeFunction.exchange(any()) }.returns(clientResponseFactory(mockResponse))

		val apps = repository.getApps().buffer().toMono().block()

		assertEquals(1, apps?.size)
		assertEquals("hello-msrl.navion-msrl", apps?.get(0)?.name)
	}
}
