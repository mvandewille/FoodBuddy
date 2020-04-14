package com.main.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.net.*
import java.io.*
import kotlin.system.exitProcess

@SpringBootApplication
class FoodbuddyApplication

fun main(args: Array<String>) {
	runApplication<FoodbuddyApplication>(*args)
}
