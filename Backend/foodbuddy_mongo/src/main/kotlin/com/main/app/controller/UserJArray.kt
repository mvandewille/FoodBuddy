package com.main.app.controller

import com.fasterxml.jackson.annotation.JsonCreator
import com.main.app.JSON.UserJ
import com.main.app.model.User
import kotlin.collections.List

data class UserJArray @JsonCreator constructor(
    val users: List<UserJ>?
)