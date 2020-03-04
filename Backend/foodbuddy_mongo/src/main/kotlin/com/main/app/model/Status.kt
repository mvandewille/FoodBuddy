package com.main.app.model

import org.springframework.data.annotation.Id
import java.text.SimpleDateFormat
import java.util.*

class Status (@Id private val id: Long, private val email: String,  private var timestamp: String, private val message: String, private val flagged: Boolean) {

    constructor(id: Long, email: String, message: String)
        : this(id, email, "00/00/0000:00-00-00", message, false)

    init {
        if(this.timestamp == "00/00/0000:00-00-00") {
            val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            this.timestamp = currentDate
        }
    }
    override fun toString(): String {
        return "Status[email=$email, message=$message, flagged=$flagged statusID=$id]"
    }

    fun getId() : Long{
        return this.id
    }

    //date format should be MM/dd/yyyy hh:mm:ss
    fun checkDate(date: String): Boolean {
        //return """^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d${'$'}""".toRegex().matches(date)
        return """^([0]\d|[1][0-2])\/([0-2]\d|[3][0-1])\/([2][01]|[1][6-9])\d{2}(\s([0-1]\d|[2][0-3])(\:[0-5]\d){1,2})?${'$'}""".toRegex().matches(date)
    }
}