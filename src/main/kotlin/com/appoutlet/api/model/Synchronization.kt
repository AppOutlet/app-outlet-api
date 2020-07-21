package com.appoutlet.api.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document
data class Synchronization(
    val date: Date,
    val store: ApplicationStore
)
