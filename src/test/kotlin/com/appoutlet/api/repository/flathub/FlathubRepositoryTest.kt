package com.appoutlet.api.repository.flathub

import com.appoutlet.api.util.clientResponseFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.kotlin.core.publisher.toMono

class FlathubRepositoryTest {
	private lateinit var flathubRepository: FlathubRepository

	private val mockExchangeFunction = mockk<ExchangeFunction>()

	@BeforeEach
	fun setup() {
		val webClient = WebClient.builder()
			.exchangeFunction(mockExchangeFunction)
			.build()

		flathubRepository = FlathubRepository(FlathubWebClient(webClient))
	}

	@Test
	fun `get apps `() {
		val mockResponse = """
			[
				{
					"flatpakAppId": "com.play0ad.zeroad",
					"name": "0 A.D.",
					"summary": "Real-Time Strategy Game of Ancient Warfare",
					"iconDesktopUrl": "/repo/appstream/x86_64/icons/128x128/com.play0ad.zeroad.png",
					"iconMobileUrl": "/repo/appstream/x86_64/icons/64x64/com.play0ad.zeroad.png",
					"currentReleaseVersion": "0.0.23",
					"currentReleaseDate": "2018-12-23T00:00:00Z",
					"inStoreSinceDate": "2017-04-18T04:14:01Z",
					"rating": 0.0,
					"ratingVotes": 0
				},
				{
					"flatpakAppId": "org.sugarlabs.AbacusActivity",
					"name": "Abacus",
					"summary": "A tool for simple arithmetic calculations",
					"iconDesktopUrl": "/repo/appstream/x86_64/icons/128x128/org.sugarlabs.AbacusActivity.png",
					"iconMobileUrl": "/repo/appstream/x86_64/icons/64x64/org.sugarlabs.AbacusActivity.png",
					"currentReleaseVersion": "61",
					"currentReleaseDate": "2020-01-21T00:00:00Z",
					"inStoreSinceDate": "2019-11-18T13:11:35.981Z",
					"rating": 0.0,
					"ratingVotes": 0
				}
			]
		""".trimIndent()
		every { mockExchangeFunction.exchange(any()) }.returns(clientResponseFactory(mockResponse))

		val apps = flathubRepository.getApps().buffer().toMono().block()

		assertEquals(2, apps?.size)
		assertEquals("0 A.D.", apps?.get(0)?.name)
		assertEquals("Abacus", apps?.get(1)?.name)
	}

	@Test
	fun `get application details `() {
		val mockResponse = """
			{
			    "flatpakAppId": "com.play0ad.zeroad",
			    "name": "0 A.D.",
			    "summary": "Real-Time Strategy Game of Ancient Warfare",
			    "description": "<p>0 A.D. is a real-time strategy (RTS) game of ancient warfare. It's a historically-based war/economy game that allows players to relive or rewrite the history of thirteen ancient civilizations, each depicted at their peak of economic growth and military prowess.</p>\n<p>The thirteen factions are: Three of the Hellenic States (Athens, Sparta and Macedonia), two of the kingdoms of Alexander the Great's successors (Seleucids and Ptolemaic Egyptians), two Celtic tribes (Britons and Gauls), the Romans, the Persians, the Iberians, the Carthaginians, the Mauryas and the Kushites. Each civilization is complete with substantially unique artwork, technologies and civilization bonuses.</p>\n",
			    "developerName": "",
			    "projectLicense": "GPL-2.0+ and CC-BY-SA",
			    "homepageUrl": "https://play0ad.com/",
			    "bugtrackerUrl": "https://trac.wildfiregames.com/",
			    "helpUrl": "",
			    "donationUrl": "https://play0ad.com/community/donate/",
			    "translateUrl": "https://trac.wildfiregames.com/wiki/Localization",
			    "iconDesktopUrl": "/repo/appstream/x86_64/icons/128x128/com.play0ad.zeroad.png",
			    "iconMobileUrl": "/repo/appstream/x86_64/icons/64x64/com.play0ad.zeroad.png",
			    "downloadFlatpakRefUrl": "/repo/appstream/com.play0ad.zeroad.flatpakref",
			    "currentReleaseVersion": "0.0.23",
			    "currentReleaseDate": "2018-12-23T00:00:00.000Z",
			    "currentReleaseDescription": "",
			    "inStoreSinceDate": "2017-04-18T04:14:01.000Z",
			    "rating": 0.0,
			    "ratingVotes": 0,
			    "categories": [
			        {
			            "name": "Game"
			        },
			        {
			            "name": "StrategyGame"
			        }
			    ],
			    "screenshots": [
			        {
			            "thumbUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/224x126/com.play0ad.zeroad-f78bee1071a9541a87ba6d2153c9679f.png",
			            "imgMobileUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/624x351/com.play0ad.zeroad-f78bee1071a9541a87ba6d2153c9679f.png",
			            "imgDesktopUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/752x423/com.play0ad.zeroad-f78bee1071a9541a87ba6d2153c9679f.png"
			        },
			        {
			            "thumbUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/224x126/com.play0ad.zeroad-877c35adb576a57d452d016b89778b62.png",
			            "imgMobileUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/624x351/com.play0ad.zeroad-877c35adb576a57d452d016b89778b62.png",
			            "imgDesktopUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/752x423/com.play0ad.zeroad-877c35adb576a57d452d016b89778b62.png"
			        },
			        {
			            "thumbUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/224x126/com.play0ad.zeroad-51d2d5d18af0fe46212c34be1b0c48f4.png",
			            "imgMobileUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/624x351/com.play0ad.zeroad-51d2d5d18af0fe46212c34be1b0c48f4.png",
			            "imgDesktopUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/752x423/com.play0ad.zeroad-51d2d5d18af0fe46212c34be1b0c48f4.png"
			        },
			        {
			            "thumbUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/224x126/com.play0ad.zeroad-61104bc4b2e9b2ccf627f1ca4ed3afea.png",
			            "imgMobileUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/624x351/com.play0ad.zeroad-61104bc4b2e9b2ccf627f1ca4ed3afea.png",
			            "imgDesktopUrl": "https://flathub.org/repo/screenshots/com.play0ad.zeroad-stable/752x423/com.play0ad.zeroad-61104bc4b2e9b2ccf627f1ca4ed3afea.png"
			        }
			    ]
			}
		""".trimIndent()

		every { mockExchangeFunction.exchange(any()) }.returns(clientResponseFactory(mockResponse))

		val appDetails = flathubRepository.getApplicationDetails("").block()
		assertEquals("0 A.D.", appDetails?.name)
	}
}
