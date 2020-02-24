package com.main.app.model

import org.springframework.data.annotation.Id

class Status (@Id private val id: Long, private val email: String, private val name: String, private val message: String, private val flagged: Boolean) {

    constructor(id: Long, email: String, name: String, message: String)
        : this(id, email, name, message, false)

    override fun toString(): String {
        return "Status[name=$name, message=$message, flagged=$flagged statusID=$id]"
    }

    fun getId() : Long{
        return this.id
    }
}