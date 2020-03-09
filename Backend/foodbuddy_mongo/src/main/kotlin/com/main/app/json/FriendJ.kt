package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class FriendJ @JsonCreator constructor(
        val email: String,
        val following: String
)