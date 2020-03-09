package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class UserAddJ @JsonCreator constructor(
        val email: String,
        val password: String?
)