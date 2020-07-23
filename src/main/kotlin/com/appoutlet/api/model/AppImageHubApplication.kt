package com.appoutlet.api.model

data class AppImageHubApplication(
	val name: String,
	val description: String,
	val categories: List<String>,
	val authors: List<Author>,
	val license: String,
	val links: List<Link>,
	val icons: List<String>,
	val screenshots: List<String>
) {
	data class Author(
		val name: String,
		val url: String
	)

	data class Link(
		val type: LinkType,
		val url: String
	)

	enum class LinkType(val typeName: String) {
		DOWNLOAD("Download"), GITHUB("GitHub")
	}
}
