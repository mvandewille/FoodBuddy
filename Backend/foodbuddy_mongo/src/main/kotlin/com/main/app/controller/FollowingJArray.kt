package com.main.app.controller

import com.fasterxml.jackson.annotation.JsonCreator

data class FollowingJArray @JsonCreator constructor(
        val content: MutableList<String>
)