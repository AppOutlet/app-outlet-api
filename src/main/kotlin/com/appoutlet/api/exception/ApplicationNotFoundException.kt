package com.appoutlet.api.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Application not found")
class ApplicationNotFoundException(message: String) : Exception(message)
