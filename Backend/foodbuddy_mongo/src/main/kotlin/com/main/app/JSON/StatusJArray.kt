package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator
import kotlin.collections.List

data class StatusJArray @JsonCreator constructor(
        val content: List<StatusJ>?
)