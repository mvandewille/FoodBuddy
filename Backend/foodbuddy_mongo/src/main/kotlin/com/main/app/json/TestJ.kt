package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class TestJ @JsonCreator constructor(
        val img: String
)