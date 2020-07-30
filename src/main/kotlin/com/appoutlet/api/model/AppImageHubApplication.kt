package com.appoutlet.api.model

import com.fasterxml.jackson.annotation.JsonValue

data class AppImageHubApplication(
    val name: String,
    val description: String?,
    val categories: List<String>?,
    val authors: List<Author>?,
    val license: String?,
    val links: List<Link>?,
    val icons: List<String>?,
    val screenshots: List<String>?
) {
	// TODO: separate this data classes on different files and group it on a package for AppImageHub models
	data class Author(
	    val name: String,
	    val url: String?
	)

	data class Link(
	    val type: LinkType?,
	    val url: String?
	)

	enum class LinkType(@JsonValue val typeName: String) {
		DOWNLOAD("Download"), GITHUB("GitHub"), INSTALL("Install")
	}
}
