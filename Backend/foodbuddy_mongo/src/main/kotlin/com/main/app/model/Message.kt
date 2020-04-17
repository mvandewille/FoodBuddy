package com.main.app.model

import org.springframework.data.annotation.Id
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Message (
        @Id private val id: Long,
        private val from: String, private val text: String, private var time: String, private var users: String?) {
    constructor(id: Long, from: String, text: String):
            this(id, from, text, "", "")
    constructor(id: Long, from: String, text: String, users: String?):
            this(id, from, text, "", users)
    init {
        if(this.time == "") {
            this.time = DateTimeFormatter
                    .ofPattern("HH:mm")
                    .withZone(ZoneOffset.systemDefault())
                    .format(Instant.now())
        }
        if(this.users == null)
            this.users = ""
    }

    fun getId(): Long {
        return this.id
    }

    fun getFrom(): String {
        return this.from
    }

    fun getText(): String {
        return this.text
    }

    override fun toString(): String {
        if(users != "")
            return this.from + ";" + this.text + ";" + this.time + ";" + this.users
        else
            return this.from + ";" + this.text + ";" + this.time

    }

}