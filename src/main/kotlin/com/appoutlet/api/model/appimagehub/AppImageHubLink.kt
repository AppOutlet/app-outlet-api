package com.appoutlet.api.model.appimagehub

import com.fasterxml.jackson.annotation.JsonValue

data class AppImageHubLink(
    val type: Type?,
    val url: String?
) {
	enum class Type(@JsonValue val typeName: String) {
		DOWNLOAD("Download"), GITHUB("GitHub"), INSTALL("Install")
	}
}
