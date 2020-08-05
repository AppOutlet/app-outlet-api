package com.appoutlet.api.model.flathub

data class FlathubApplication(
    val flatpakAppId: String,
    val name: String,
    val summary: String,
    val iconDesktopUrl: String,
    val iconMobileUrl: String,
    val currentReleaseVersion: String,
    val currentReleaseDate: String?,
    val inStoreSinceDate: String,
    val rating: Int,
    val ratingVotes: Int
)
