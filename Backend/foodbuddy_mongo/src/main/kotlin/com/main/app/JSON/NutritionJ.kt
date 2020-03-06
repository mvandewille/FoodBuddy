package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class NutritionJ @JsonCreator constructor(
        var calories: Int,
        var sodium: Double,
        var carbs: Double,
        var protein: Double,
        var fat: Double,
        var cholesterol: Double
)