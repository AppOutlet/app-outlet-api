package com.appoutlet.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
class AppOutletApiApplication

fun main(args: Array<String>) {
    runApplication<AppOutletApiApplication>(*args)
}
