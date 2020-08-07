package com.appoutlet.api.controller

import com.appoutlet.api.service.application.AppService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("apps")
class AppController(
    private val appService: AppService
) {
	@GetMapping
	fun getAllApplications() = appService.findAll()
}
