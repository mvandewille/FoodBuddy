package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class AllergenJ @JsonCreator constructor(
        val name: String
)
