package com.appoutlet.api.repository

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AppOutletApplicationRepository : ReactiveMongoRepository<AppOutletApplication, String>
