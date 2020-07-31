package com.appoutlet.api.model.snapstore
import com.fasterxml.jackson.annotation.JsonProperty

data class Embedded(
	@JsonProperty("clickindex:package")
	val application: List<SnapStoreApplication>
)
