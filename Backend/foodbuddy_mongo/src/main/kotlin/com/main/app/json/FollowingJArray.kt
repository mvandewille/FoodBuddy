package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class FollowingJArray @JsonCreator constructor(
        val content: MutableList<String>
)