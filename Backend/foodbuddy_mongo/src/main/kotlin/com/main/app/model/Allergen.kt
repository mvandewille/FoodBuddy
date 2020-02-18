package com.main.app.model

import com.sun.xml.fastinfoset.util.StringArray
import org.springframework.data.annotation.Id

//https://codeboje.de/spring-data-mongodb/

class Allergen (@Id private val name: String){

    //Override the toString function to what we want it to be.
    override fun toString(): String {
        return "Allergen[Name=$name]"
    }
}