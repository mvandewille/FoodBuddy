package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class DayFoodJ @JsonCreator constructor(
        val food: String,
        val amount: Double,
        val timestamp: String
)