package com.appoutlet.api.repository

import com.appoutlet.api.model.Synchronization
import org.springframework.data.mongodb.repository.MongoRepository

interface SynchronizationRepository : MongoRepository<Synchronization, String>
