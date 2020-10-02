package com.appoutlet.api.model.snapstore

import java.util.Date

data class SnapStoreApplication(
	// TODO: rename these properties and use @JsonProperty annotation do bind jackson serialization
    val confinement: String?,
    val date_published: Date?,
    val description: String?,
    val developer_name: String,
    val download_url: String?,
    val icon_url: String?,
    val last_updated: Date?,
    val license: String?,
    val name: String,
    val package_name: String?,
    val screenshot_urls: List<String>?,
    val snap_id: String,
    val summary: String?,
    val support_url: String?,
    val version: String?,
    val website: String?
)
