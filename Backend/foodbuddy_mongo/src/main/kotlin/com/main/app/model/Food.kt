package com.main.app.model

import com.main.app.json.FoodJ
import org.springframework.data.annotation.Id

//https://hellokoding.com/jpa-one-to-one-foreignkey-relationship-example-with-spring-boot-maven-and-mysql/
//this link has information on relationships in springboot

class Food (@Id private val name: String, private val calories: Int,
            private val sodium: Double?, private val carbs: Double?, private val protein: Double?,
            private val fat: Double?, private val cholesterol: Double?) {

    constructor(name: String)
            : this(name, 0, 0.0, 0.0, 0.0, 0.0, 0.0)

    constructor(name: String, calories: Int)
            : this(name, calories, 0.0, 0.0, 0.0, 0.0, 0.0)

    override fun toString(): String {
        return "Food[name=$name, calories=$calories]"
    }

    fun getName(): String {
        return this.name
    }

    fun getCalories(): Int {
        return this.calories
    }

    fun getSodium(): Double {
        if (this.sodium == null)
            return 0.0
        else
            return this.sodium
    }

    fun getCarbs(): Double {
        if (this.carbs == null)
            return 0.0
        else
            return this.carbs
    }

    fun getProtein(): Double {
        if (this.protein == null)
            return 0.0
        else
            return this.protein
    }

    fun getFat(): Double {
        if (this.fat == null)
            return 0.0
        else
            return this.fat
    }

    fun getCholesterol(): Double {
        if (this.cholesterol == null)
            return 0.0
        else
            return this.cholesterol
    }

    fun toJson(): FoodJ {
        return FoodJ(this.name, this.calories, this.sodium, this.carbs, this.protein, this.fat, this.cholesterol)
    }

}