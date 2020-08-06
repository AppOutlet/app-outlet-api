package com.appoutlet.api.model.flathub

data class FlathubApplication(
    val flatpakAppId: String,
    val name: String? = null,
    val summary: String? = null,
    val iconDesktopUrl: String? = null,
    val iconMobileUrl: String? = null,
    val currentReleaseVersion: String? = null,
    val currentReleaseDate: String? = null,
    val inStoreSinceDate: String? = null,
    val rating: Int? = null,
    val ratingVotes: Int? = null
)
