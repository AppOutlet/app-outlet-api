package com.appoutlet.api.model.flathub

import java.util.Date

data class FlathubApplicationDetails(
    val flatpakAppId: String,
    val name: String,
    val summary: String?,
    val description: String?,
    val developerName: String?,
    val projectLicense: String?,
    val homepageUrl: String?,
    val bugtrackerUrl: String?,
    val donationUrl: String?,
    val iconDesktopUrl: String?,
    val downloadFlatpakRefUrl: String,
    val currentReleaseVersion: String?,
    val currentReleaseDate: Date?,
    val inStoreSinceDate: Date?,
    val categories: List<FlathubCategory>?,
    val screenshots: List<FlathubScreenshot>?
)
