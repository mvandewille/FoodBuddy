package com.main.app.model

import org.springframework.data.annotation.Id

class Status (@Id private val name: String, private val message: String, private val flagged: Boolean, private val statusID: Long) {

    constructor(name: String, message: String, flagged: Boolean)
        : this(name, message, flagged, 0)

    override fun toString(): String {
        return "Status[name=$name, message=$message, flagged=$flagged statusID=$statusID]"
    }

    fun getStatusID() : Long{
        return this.statusID
    }
}