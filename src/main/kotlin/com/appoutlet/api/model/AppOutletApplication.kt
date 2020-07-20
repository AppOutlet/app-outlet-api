package com.appoutlet.api.model

import java.util.Date

data class AppOutletApplication(
    val id: String,
    val name: String,
    val summary: String,
    val description: String,
    val developer: String,
    val license: String?,
    val homepage: String,
    val bugtrackerUrl: String,
    val donationUrl: String,
    val icon: String?,
    val downloadUrl: String?,
    val version: String,
    val lastReleaseDate: Date?,
    val creationDate: Date,
    val tags: List<String>,
    val screenshots: List<String>,
    val store: ApplicationStore,
    val packageType: ApplicationPackageType
)
