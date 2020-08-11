package com.appoutlet.api.model.appimagehub

data class AppImageHubApplication(
	val name: String,
	val description: String? = null,
	val categories: List<String>? = null,
	val authors: List<AppImageHubAuthor>? = null,
	val license: String? = null,
	val links: List<AppImageHubLink>? = null,
	val icons: List<String>? = null,
	val screenshots: List<String>? = null
)
