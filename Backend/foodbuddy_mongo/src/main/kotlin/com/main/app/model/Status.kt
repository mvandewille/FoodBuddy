package com.main.app.model

import org.springframework.data.annotation.Id

class Status (@Id private val id: Long, private val email: String,  private val timestamp: String, private val message: String, private val flagged: Boolean) {

    constructor(id: Long, email: String, name: String, message: String)
        : this(id, email, name, message, false)

    override fun toString(): String {
        return "Status[email=$email, message=$message, flagged=$flagged statusID=$id]"
    }

    fun getId() : Long{
        return this.id
    }

    //date format should be MM/dd/yyyy:hh-mm-ss
    fun checkDate(date: String): Boolean {
        return """^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d${'$'}""".toRegex().matches(date)
    }
}