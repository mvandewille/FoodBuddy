package com.main.app.model

import org.springframework.data.annotation.Id
import java.util.*

class Day (@Id private val date: Date){

    //Override the toString function to what we want it to be.
    override fun toString(): String {
        return "Day[Date=$date]"
    }
}