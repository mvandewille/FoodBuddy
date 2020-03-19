package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class StatusLikeJ @JsonCreator constructor(
        val id: Long
)
