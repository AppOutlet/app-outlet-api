package com.appoutlet.api.model

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
    val helpUrl: String,
    val donationUrl: String,
    val translateUrl: String,
    val iconDesktopUrl: String,
    val iconMobileUrl: String,
    val downloadFlatpakRefUrl: String,
    val currentReleaseVersion: String,
    val currentReleaseDate: Date?,
    val currentReleaseDescription: String,
    val inStoreSinceDate: Date,
    val rating: Int,
    val ratingVotes: Int,
    val categories: List<FlathubCategory>,
    val screenshots: List<FlathubScreenshot>
)
