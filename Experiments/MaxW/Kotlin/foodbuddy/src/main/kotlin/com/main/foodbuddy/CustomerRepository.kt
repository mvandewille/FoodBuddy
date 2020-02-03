package com.main.foodbuddy

import kotlin.collections.MutableList
import org.springframework.data.mongodb.repository.MongoRepository

interface CustomerRepository: MongoRepository<Customer, String> {
    fun findByFirstName(firstName: String): Customer
    fun findByLastName(lastName: String): MutableList<Customer>
    fun findById(id: Long): Customer

}