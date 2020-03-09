package com.main.app.model

import com.main.app.json.DayFoodJ
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DayFood(private val food: String, private val amount: Double, private var timestamp: String) {

    constructor(food: String, amount: Double)
        : this(food, amount, "00/00/0000 00:00:00")

    init {
        if(this.timestamp == "00/00/0000 00:00:00") {
            this.timestamp = DateTimeFormatter
                    .ofPattern("MM-dd-yyyy HH:mm:ss.SS")
                    .withZone(ZoneOffset.systemDefault())
                    .format(Instant.now())
        }
    }

    fun getName(): String {
        return this.food
    }

    fun getAmount(): Double {
        return this.amount
    }

    fun toJson(): DayFoodJ {
        return DayFoodJ(this.food, this.amount, this.timestamp)
    }
}