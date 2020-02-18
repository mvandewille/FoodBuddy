package com.main.app.model

import org.springframework.data.annotation.Id

class Day (@Id private val date: String){

    //Override the toString function to what we want it to be.
    override fun toString(): String {
        return date
    }
}