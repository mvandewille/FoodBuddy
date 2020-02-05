package com.main.model

import javax.persistence.Id
import javax.persistence.Entity
import javax.validation.constraints.NotBlank

@Entity
data class User (@Id val email: String, @get: NotBlank private val name: String,
            @get: NotBlank private val height_in: Int, @get: NotBlank private val weight_lb: Int,
            @get: NotBlank private val calorie_limit: Int, @get: NotBlank private val user_password: String,
            @get: NotBlank private val user_type : String) {

    override fun toString(): String {
        return "User{email: $email, name: $name, user_type: $user_type]"
    }
}