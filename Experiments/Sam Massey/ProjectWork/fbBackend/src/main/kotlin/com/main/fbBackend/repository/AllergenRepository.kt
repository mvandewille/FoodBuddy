package com.main.fbBackend.repository

import com.main.fbBackend.model.Allergen
import kotlin.collections.MutableList
import org.springframework.data.mongodb.repository.MongoRepository

interface AllergenRepository : MongoRepository<Allergen, String>{
    //Function to show all allergens
    fun findAllBy(): MutableList<Allergen>
    //Function find an allergen by a name
    fun findByName(name: String): MutableList<Allergen>
    //Delete an allergen based off of its name
    fun deleteByName(name: String)
}