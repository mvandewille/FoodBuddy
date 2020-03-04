package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class StatusJ @JsonCreator constructor(
        val email: String, //foreign key to posting user's email
        val timestamp: String?,
        val flagged: Boolean?,
        val message: String
)