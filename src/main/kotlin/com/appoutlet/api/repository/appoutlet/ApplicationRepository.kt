package com.appoutlet.api.repository.appoutlet

import com.appoutlet.api.model.appoutlet.AppOutletApplication
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ApplicationRepository : ReactiveMongoRepository<AppOutletApplication, String>
