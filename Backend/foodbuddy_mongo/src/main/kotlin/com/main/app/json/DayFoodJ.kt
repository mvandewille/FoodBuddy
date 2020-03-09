package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class DayFoodJ @JsonCreator constructor(
        val food: String,
        val amount: Double,
        val timestamp: String
)