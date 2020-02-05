package com.main.foodbuddy

import org.springframework.data.annotation.Id

class Customer (private val firstName: String, private val lastName: String, @Id val id: Long) {

    override fun toString(): String {
        return "Customer[id=%s, firstName='%s', lastName='%s']".format(id, firstName, lastName)

    }

}