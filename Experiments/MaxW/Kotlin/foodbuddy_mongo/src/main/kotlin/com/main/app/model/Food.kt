package com.main.app.model

import org.springframework.data.annotation.Id

//https://hellokoding.com/jpa-one-to-one-foreignkey-relationship-example-with-spring-boot-maven-and-mysql/
//this link has information on relationships in springboot

class Food (@Id private val name: String, private val calories: Int,
            private val sodium: Double, private val carbs: Double, private val protein: Double,
            private val fat: Double, private val cholesterol: Double) {

    constructor(name: String)
            : this(name, 0, 0.0, 0.0, 0.0, 0.0, 0.0)

    constructor(name: String, calories: Int)
            : this(name, calories, 0.0, 0.0, 0.0, 0.0, 0.0)

    override fun toString(): String {
        return "Food[name=$name, calories=$calories]"
    }

}