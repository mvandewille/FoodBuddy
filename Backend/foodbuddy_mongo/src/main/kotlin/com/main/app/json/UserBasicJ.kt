package com.main.app.json

import com.fasterxml.jackson.annotation.JsonCreator

data class UserBasicJ @JsonCreator constructor(
        val email: String,
        val name: String?,
        val age: Int?,
        val height: Int?,
        val weight: Int?,
        val lifestyle: String?,
        val gender: String?,
        val calorieLimit: Int?,
        val userType: String?,
        val allergens: MutableList<String>?
)