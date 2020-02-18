package com.main.app.model

import org.springframework.data.annotation.Id

//https://hellokoding.com/jpa-one-to-one-foreignkey-relationship-example-with-spring-boot-maven-and-mysql/
//this link has information on relationships in springboot

class User (@Id private var email: String, private var name: String?, private var height: Int?,
            private var weight: Int?, private var calorieLimit: Int?, private var password: String,
            private var lifestyle: String?, private var userType: String) {

    constructor(email: String, name: String, password: String)
            : this(email, name, null, null, null, password, null, "default")

    constructor(email: String, password: String)
            : this(email, null, null, null, null, password, null, "default")

    override fun toString(): String {
        return "User[email=$email, name=$name, userType=$userType]"
    }

    fun setExtras(name: String?, height: Int?, weight: Int?, lifestyle: String?) {
        this.name = name
        this.height = height
        this.weight = weight
        this.lifestyle = lifestyle
    }

}