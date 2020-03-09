package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class ResponseJ @JsonCreator constructor(
        val response: Int,
        val message: String
)