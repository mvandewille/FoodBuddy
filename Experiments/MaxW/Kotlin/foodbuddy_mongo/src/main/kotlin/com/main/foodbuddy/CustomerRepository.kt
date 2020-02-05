package com.main.foodbuddy

import kotlin.collections.MutableList
import org.springframework.data.mongodb.repository.MongoRepository
//import org.springframework.data.mongodb.MongoDbFactory possible alternative

interface CustomerRepository: MongoRepository<Customer, String> {
    fun findByFirstName(firstName: String): MutableList<Customer>
    fun findByLastName(lastName: String): MutableList<Customer>
    fun findByFirstNameAndLastName(firstName: String, lastName: String): MutableList<Customer>
    fun findById(id: Long): Customer
    fun findByOrderByIdDesc(): MutableList<Customer>

}