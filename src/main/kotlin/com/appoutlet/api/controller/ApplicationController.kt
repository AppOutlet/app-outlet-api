package com.appoutlet.api.controller

import com.appoutlet.api.service.application.ApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("apps")
class ApplicationController(
    private val applicationService: ApplicationService
) {
	@GetMapping
	fun getAllApplications() = applicationService.findAll()

	@PostMapping("{appId}/view")
	fun registerAppView(@PathVariable appId: String) = applicationService.registerVisualization(appId)

	@GetMapping("search")
	fun search(@RequestParam("searchTerm") searchTerm: String) = applicationService.search(searchTerm)
}
