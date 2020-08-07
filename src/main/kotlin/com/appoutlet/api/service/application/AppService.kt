package com.appoutlet.api.service.application

import com.appoutlet.api.repository.AppOutletApplicationRepository
import org.springframework.stereotype.Service

@Service
class AppService(
    private val appOutletApplicationRepository: AppOutletApplicationRepository
) {
	fun findAll() = appOutletApplicationRepository.findAll()
}
