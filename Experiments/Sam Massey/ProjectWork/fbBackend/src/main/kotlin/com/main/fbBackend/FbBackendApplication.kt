package com.main.fbBackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FbBackendApplication

fun main(args: Array<String>) {
	runApplication<FbBackendApplication>(*args)
}
