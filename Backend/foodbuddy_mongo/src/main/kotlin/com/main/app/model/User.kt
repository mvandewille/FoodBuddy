package com.main.app.model

import com.main.app.JSON.FoodJ
import com.main.app.JSON.UserBasicJ
import com.main.app.JSON.UserJ
import com.sun.xml.fastinfoset.util.StringArray
import org.springframework.data.annotation.Id
import kotlin.collections.MutableList

//https://hellokoding.com/jpa-one-to-one-foreignkey-relationship-example-with-spring-boot-maven-and-mysql/
//this link has information on relationships in springboot

class User (@Id private var email: String, private var name: String?, private var age: Int?,
            private var height: Int?, private var weight: Int?, private var calorieLimit: Int?,
            private var password: String, private var gender: String?, private var lifestyle: String?,
            private var userType: String, private var allergens: MutableList<String>,
            private val following: MutableList<String>, private val foods: MutableList<Food>) {

    constructor(email: String, password: String)
            : this(email, null, null, null, null, null, password, null, null, "default", mutableListOf<String>(), mutableListOf<String>(), mutableListOf<Food>())

    fun getFoods(): MutableList<Food> {
        return this.foods
    }

    fun getFollowing(): MutableList<String> {
        return this.following
    }

    fun toJson(): UserJ {
        val tempFoods = mutableListOf<FoodJ>()
        this.foods.forEach { tempFoods.add(it.toJson()) }
        return UserJ(this.email, null, this.name, this.age, this.height, this.weight, this.lifestyle, this.gender, this.calorieLimit, this.userType, this.allergens, this.following, tempFoods)
    }

    fun toBasicJson(): UserBasicJ {
        return UserBasicJ(this.email, null, this.name, this.age, this.height, this.weight, this.lifestyle, this.gender, this.calorieLimit, this.userType, this.allergens)
    }

    fun setExtras(name: String?, age: Int?, height: Int?, weight: Int?, lifestyle: String?, gender: String?, calorieLimit: Int?, allergens: MutableList<String>?): Boolean {
        if(name != null) {this.name = name}
        if(age != null) {this.age = age}
        if(height != null) {this.height = height}
        if(weight != null) {this.weight = weight}
        if(lifestyle != null) {this.lifestyle = lifestyle}
        if(gender != null) {this.gender = gender}
        if(allergens != null) {this.allergens = allergens}
        if(calorieLimit != null) {this.calorieLimit = calorieLimit}

        return true
    }

    fun addFood(name: String, calories: Int, sodium: Double?, carbs: Double?, protein: Double?, fat: Double?, cholesterol: Double?): Boolean {
        val newFood = Food(name, calories, sodium, carbs, protein, fat, cholesterol)

        this.foods.add(newFood)
        return true
    }

    fun addFollowing(email: String): Boolean{
        this.following.add(email)
        return true
    }
}