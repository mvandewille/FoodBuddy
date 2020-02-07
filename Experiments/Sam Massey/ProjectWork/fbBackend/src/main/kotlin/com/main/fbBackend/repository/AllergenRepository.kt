package com.main.fbBackend.repository

import org.springframework.data.annotation.Id

class AllergenRepository (@Id private val Name: String){

    //Override the toString function to what we want it to be.
    override fun toString(): String {
        return "Allergen[Name=$Name]"
    }
}