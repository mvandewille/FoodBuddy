package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class UserJ @JsonCreator constructor(
        val email: String,
        val name: String,
        val password: String
)