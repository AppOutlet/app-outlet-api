package com.appoutlet.api.controller

import com.appoutlet.api.model.ServerStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("status")
class StatusController {
	@GetMapping
	fun getStatus() = ServerStatus("Running")
}
