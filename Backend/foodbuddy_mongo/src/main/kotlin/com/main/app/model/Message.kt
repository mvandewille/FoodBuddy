package com.main.app.model

import org.springframework.data.annotation.Id
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Message (
        @Id private val id: Long,
        private val from: String, private val text: String, private var time: String) {
    constructor(id: Long, from: String, text: String):
            this(id, from, text, "")
    init {
        if(this.time == "") {
            this.time = DateTimeFormatter
                    .ofPattern("MM/dd/yyyy HH:mm:ss")
                    .withZone(ZoneOffset.systemDefault())
                    .format(Instant.now())
        }
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
        return this.from + ": " + this.text
    }

}