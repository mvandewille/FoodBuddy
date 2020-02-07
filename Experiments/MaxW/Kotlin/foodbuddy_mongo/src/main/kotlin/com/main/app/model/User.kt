package com.main.app.model

import org.springframework.data.annotation.Id

class User (@Id private val email: String, private val name: String, private val height: Int,
            private val weight: Int, private val calorieLimit: Int, private val password: String,
            private val userType: String) {

    override fun toString(): String {
        return "User[email=$email, name=$name, height=$height, weight=$weight, calorieLimit=$calorieLimit]"
    }

}