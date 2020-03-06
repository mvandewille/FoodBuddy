package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class DayJ @JsonCreator constructor(
        val foods: List<DayFoodJ>
)