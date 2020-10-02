package com.appoutlet.api.model.appoutlet

import com.appoutlet.api.model.ApplicationPackageType
import com.appoutlet.api.model.ApplicationStore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document
data class AppOutletApplication(
    @Id
	val id: String,
    val name: String,
    val summary: String? = null,
    val description: String? = null,
    val developer: String?,
    val license: String? = null,
    val homepage: String? = null,
    val bugtrackerUrl: String? = null,
    val donationUrl: String? = null,
    val icon: String? = null,
    val downloadUrl: String? = null,
    val version: String? = null,
	// TODO: replace Date by LocalDateTime
    val lastReleaseDate: Date? = null,
    val creationDate: Date? = null,
    val tags: List<String>? = null,
    val screenshots: List<String>? = null,
    val store: ApplicationStore,
    val packageType: ApplicationPackageType,
    val packageName: String? = null,
    val confinement: String? = null,
    val viewCount: Int? = null
)
