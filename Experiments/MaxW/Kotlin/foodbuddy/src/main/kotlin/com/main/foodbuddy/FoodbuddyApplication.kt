package com.main.foodbuddy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FoodbuddyApplication {

	lateinit var repository: CustomerRepository
/*
	@Override
	fun run(args: String) {
		repository.deleteAll()

		repository.save(Customer("Alice", "Smith", 0))
		repository.save(Customer("Bob", "Smith"))

		println("Customers found with findAll(): ")
		println("--------------------------------")
		repository.findAll().forEach {
			println(it)
		}
		println()


	}
	*/
}


fun main(args: Array<String>) {
	runApplication<FoodbuddyApplication>(*args)
}
