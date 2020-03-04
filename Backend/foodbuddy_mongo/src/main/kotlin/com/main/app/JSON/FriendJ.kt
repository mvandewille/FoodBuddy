package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator

data class FriendJ @JsonCreator constructor(
        val email: String,
        val friend: String
)