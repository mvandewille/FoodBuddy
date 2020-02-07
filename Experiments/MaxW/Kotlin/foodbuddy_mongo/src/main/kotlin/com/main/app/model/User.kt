package com.main.app.model

import org.springframework.data.annotation.Id

class User (@Id private val email: String, private val name: String, private val height: Int?,
            private val weight: Int?, private val calorieLimit: Int?, private val password: String,
            private val userType: String) {

    constructor(email: String, name: String, password: String, userType: String)
            : this(email, name, null, null, null, password, userType)

    override fun toString(): String {
        return "User[email=$email, name=$name, userType=$userType]"
    }

}