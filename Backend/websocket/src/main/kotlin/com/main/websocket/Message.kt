package com.main.websocket

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


data class Message (val name: String, val message: String, var time: String) {

    constructor(tokens: List<String>)
            : this(tokens[0], tokens[1], "")
    constructor(name: String, message: String)
        : this(name, message, "")

    init {
        if(this.time == "") {
            this.time = DateTimeFormatter
                    .ofPattern("MM/dd/yyyy HH:mm:ss")
                    .withZone(ZoneOffset.systemDefault())
                    .format(Instant.now())
        }
    }

    fun format(): String {
        return this.name + ";" + this.message + ";" + this.time
    }

    fun print() {
        println(this.time.split(" ")[1] + " " + this.name + ": " + this.message)
    }
}