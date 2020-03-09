package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class DayJ @JsonCreator constructor(
        val foods: List<DayFoodJ>
)