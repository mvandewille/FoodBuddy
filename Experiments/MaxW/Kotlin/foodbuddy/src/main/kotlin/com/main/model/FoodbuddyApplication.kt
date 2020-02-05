package com.main.model

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FoodbuddyApplication

fun main(args: Array<String>) {
	runApplication<FoodbuddyApplication>(*args)
}
