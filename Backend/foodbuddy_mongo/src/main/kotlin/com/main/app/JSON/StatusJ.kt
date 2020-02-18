package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class StatusJ @JsonCreator constructor(
        val name: String,
        val message: String
)