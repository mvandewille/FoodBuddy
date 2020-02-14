package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class FoodJ @JsonCreator constructor(
        val name: String,
        val calories: Int,
        val sodium: Double,
        val carbs: Double,
        val protein: Double,
        val fat: Double,
        val cholesterol: Double
)