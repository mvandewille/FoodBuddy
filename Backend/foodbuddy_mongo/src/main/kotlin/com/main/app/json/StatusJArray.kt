package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator
import kotlin.collections.List

data class StatusJArray @JsonCreator constructor(
        val content: List<StatusJ>?
)