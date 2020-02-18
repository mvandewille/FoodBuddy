package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

data class DayJ @JsonCreator constructor(
        val date: String
)