package com.main.app.model

import com.main.app.json.DayFoodJ
import com.main.app.json.DayJ

class Day (private val foods: MutableList<DayFood>){

    constructor()
        : this(mutableListOf<DayFood>())

    fun getFoods(): MutableList<DayFood> {
        return foods
    }
    fun addFood(food: String, amount: Double) {
        foods.add(DayFood(food, amount))
    }

    fun toJson(): DayJ {
        val tempFoods = mutableListOf<DayFoodJ>()
        this.foods.forEach { tempFoods.add(it.toJson()) }
        return DayJ(tempFoods)
    }

}