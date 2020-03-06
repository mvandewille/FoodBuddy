package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class UserAddJ @JsonCreator constructor(
        val email: String,
        val password: String?
)