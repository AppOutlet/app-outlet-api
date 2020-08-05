package com.appoutlet.api.model.appimagehub

data class AppImageHubApplication(
    val name: String,
    val description: String?,
    val categories: List<String>?,
    val authors: List<AppImageHubAuthor>?,
    val license: String?,
    val appImageHubLinks: List<AppImageHubLink>?,
    val icons: List<String>?,
    val screenshots: List<String>?
)
