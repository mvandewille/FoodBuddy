package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class UserJ @JsonCreator constructor(
        val email: String,
        val name: String?,
        val age: Int?,
        val height: Int?,
        val weight: Int?,
        val lifestyle: String?,
        val gender: String?,
        val calorieLimit: Int?,
        val userType: String?,
        val allergens: MutableList<String>?,
        val following: MutableList<String>?,
        val foods: MutableList<FoodJ>?,
        val calendar: MutableMap<String, DayJ>?
)