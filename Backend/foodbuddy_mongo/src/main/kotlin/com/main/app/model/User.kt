package com.main.app.model

import com.main.app.JSON.UserJ
import com.sun.xml.fastinfoset.util.StringArray
import org.springframework.data.annotation.Id

//https://hellokoding.com/jpa-one-to-one-foreignkey-relationship-example-with-spring-boot-maven-and-mysql/
//this link has information on relationships in springboot

class User (@Id private var email: String, private var name: String?, private var age: Int?,
            private var height: Int?, private var weight: Int?, private var calorieLimit: Int?,
            private var password: String, private var gender: String?, private var lifestyle: String?,
            private var userType: String, private var allergens: Array<String>?) {

    constructor(email: String, password: String)
            : this(email, null, null, null, null, null, password, null, null, "default", null)

    override fun toString(): String {
        return "User[email=$email, name=$name, userType=$userType]"
    }

    fun toJson(): UserJ {
        return UserJ(this.email, null, this.name, this.age, this.height, this.weight, this.lifestyle, this.gender, this.calorieLimit, this.userType, this.allergens)
    }

    fun setExtras(name: String?, age: Int?, height: Int?, weight: Int?, lifestyle: String?, gender: String?, calorieLimit: Int?, allergens: Array<String>?) {
        this.name = name
        this.age = age
        this.height = height
        this.weight = weight
        this.lifestyle = lifestyle
        this.gender = gender
        this.calorieLimit = calorieLimit
        this.allergens = allergens
    }

}