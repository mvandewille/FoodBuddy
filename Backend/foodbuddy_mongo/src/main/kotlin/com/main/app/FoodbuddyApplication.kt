package com.main.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class FoodbuddyApplication

fun main(args: Array<String>) {
	runApplication<FoodbuddyApplication>(*args)
}
