package com.main.fbBackend.model

import org.springframework.data.annotation.Id

class Day (@Id private val Date: String){

    //Override the toString function to what we want it to be.
    override fun toString(): String {
        return "Day[Date=$Date]"
    }
}