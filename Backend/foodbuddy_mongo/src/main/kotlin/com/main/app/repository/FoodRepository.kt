package com.main.app.repository

import com.main.app.model.Food
import kotlin.collections.MutableList
import org.springframework.data.mongodb.repository.MongoRepository

interface FoodRepository : MongoRepository<Food, String> {
    fun findAllBy(): MutableList<Food>
    fun findByName(name: String): Food

    //Query by Calories
    fun findByOrderByCaloriesAsc(): MutableList<Food>
    fun findByOrderByCaloriesDesc(): MutableList<Food>

    fun deleteByName(name: String): Food
}