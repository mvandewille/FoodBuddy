package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator
import kotlin.collections.List

data class UserJArray @JsonCreator constructor(
    val users: List<UserJ>?
)