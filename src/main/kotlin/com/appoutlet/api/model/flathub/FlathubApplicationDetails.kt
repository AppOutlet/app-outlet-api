package com.appoutlet.api.model.flathub

import java.util.Date

data class FlathubApplicationDetails(
    val flatpakAppId: String,
    val name: String,
    val summary: String,
    val description: String,
    val developerName: String,
    val projectLicense: String?,
    val homepageUrl: String,
    val bugtrackerUrl: String,
    val helpUrl: String? = null,
    val donationUrl: String,
    val translateUrl: String? = null,
    val iconDesktopUrl: String,
    val iconMobileUrl: String? = null,
    val downloadFlatpakRefUrl: String,
    val currentReleaseVersion: String,
    val currentReleaseDate: Date?,
    val currentReleaseDescription: String? = null,
    val inStoreSinceDate: Date,
    val rating: Int? = null,
    val ratingVotes: Int? = null,
    val categories: List<FlathubCategory>,
    val screenshots: List<FlathubScreenshot>
)
