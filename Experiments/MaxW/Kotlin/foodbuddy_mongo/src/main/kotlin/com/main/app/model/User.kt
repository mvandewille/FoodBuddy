package com.main.app.model

import org.springframework.data.annotation.Id

//https://hellokoding.com/jpa-one-to-one-foreignkey-relationship-example-with-spring-boot-maven-and-mysql/
//this link has information on relationships in springboot

class User (@Id private val email: String, private val name: String, private val height: Int?,
            private val weight: Int?, private val calorieLimit: Int?, private val password: String,
            private val userType: String) {

    constructor(email: String, name: String, password: String, userType: String)
            : this(email, name, null, null, null, password, userType)

    override fun toString(): String {
        return "User[email=$email, name=$name, userType=$userType]"
    }

}