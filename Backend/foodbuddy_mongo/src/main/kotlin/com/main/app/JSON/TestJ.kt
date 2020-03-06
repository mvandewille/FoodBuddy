package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class TestJ @JsonCreator constructor(
        val img: String
)