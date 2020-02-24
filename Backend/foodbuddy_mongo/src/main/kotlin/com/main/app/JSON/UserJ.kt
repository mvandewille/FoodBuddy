package com.main.app.JSON

import com.fasterxml.jackson.annotation.JsonCreator
import com.sun.xml.fastinfoset.util.StringArray

data class UserJ @JsonCreator constructor(
        val email: String,
        val password: String?,
        val name: String?,
        val height: Int?,
        val weight: Int?,
        val lifestyle: String?,
        val gender: String?,
        val calorieLimit: Int?,
        val userType: String?,
        val allergens: Array<String>?
)