package com.main.fbBackend.model

import org.springframework.data.annotation.Id

class Status (@Id private val name: String, private val message: String, private val flagged: Boolean) {

    override fun toString(): String {
        return "Status[name=$name, message=$message, flagged=$flagged"
    }
    
}