package com.main.app.model

import com.main.app.JSON.DayJ
import com.main.app.JSON.FoodJ
import com.main.app.JSON.UserBasicJ
import com.main.app.JSON.UserJ
import org.springframework.data.annotation.Id
import kotlin.collections.MutableList

class User (@Id private var email: String, private var name: String?, private var age: Int?,
            private var height: Int?, private var weight: Int?, private var calorieLimit: Int?,
            private var password: String, private var gender: String?, private var lifestyle: String?,
            private var userType: String, private var allergens: MutableList<String>,
            private val following: MutableList<String>, private val foods: MutableList<Food>,
            private val calendar: MutableMap<String, Day>) {

    constructor(email: String, password: String)
            : this(email, null, null, null, null, null, password, null, null, "default", mutableListOf<String>(), mutableListOf<String>(), mutableListOf<Food>(), mutableMapOf<String, Day>())

    fun getFoods(): MutableList<Food> {
        return this.foods
    }

    fun getFood(name: String): Food? {
        foods.forEach {
            if (it.getName() == name)
                return it
        }
        return null
    }
    fun getCalendar(): MutableMap<String, Day> {
        return calendar
    }

    fun getFollowing(): MutableList<String> {
        return this.following
    }

    fun toJson(): UserJ {
        val tempCal = mutableMapOf<String, DayJ>()
        this.calendar.forEach { tempCal[it.key] = it.value.toJson() }
        val tempFoods = mutableListOf<FoodJ>()
        this.foods.forEach { tempFoods.add(it.toJson()) }
        return UserJ(this.email, this.name, this.age, this.height, this.weight, this.lifestyle, this.gender, this.calorieLimit, this.userType, this.allergens, this.following, tempFoods, tempCal)
    }

    fun toBasicJson(): UserBasicJ {
        return UserBasicJ(this.email, this.name, this.age, this.height, this.weight, this.lifestyle, this.gender, this.calorieLimit, this.userType, this.allergens)
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

    fun addFood(food: Food, amount: Double, date: String): Boolean {
        if (!this.foods.contains(food))
            this.foods.add(food)
        if (this.checkDateExists(date))
            this.calendar[date]?.addFood(food.getName(), amount)
        else {
            this.calendar[date] = Day()
            this.calendar[date]?.addFood(food.getName(), amount)
        }
        return true
    }

    fun addFollowing(email: String): Boolean {
        this.following.add(email)
        return true
    }

    fun checkDateExists(date: String): Boolean {
        return this.calendar.keys.contains(date)
    }
}