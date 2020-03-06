package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class FoodAddJ @JsonCreator constructor(
        val email: String,
        val name: String,
        val calories: Int,
        val sodium: Double?,
        val carbs: Double?,
        val protein: Double?,
        val fat: Double?,
        val cholesterol: Double?,
        val amount: Double
)