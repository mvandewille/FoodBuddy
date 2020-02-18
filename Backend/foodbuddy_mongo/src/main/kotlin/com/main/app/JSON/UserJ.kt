package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class UserJ @JsonCreator constructor(
        val email: String,
        val password: String?,
        val name: String?,
        val height: Int?,
        val weight: Int?,
        val lifestyle: String?
)