package com.appoutlet.api.repository

import com.appoutlet.api.model.Synchronization
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface SynchronizationRepository : ReactiveMongoRepository<Synchronization, String>
