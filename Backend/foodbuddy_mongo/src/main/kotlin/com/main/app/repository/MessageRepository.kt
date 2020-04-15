package com.main.app.repository

import com.main.app.model.Message
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository : MongoRepository<Message, String> {

    fun findAllByOrderByIdDesc() : MutableList<Message>

    fun deleteAllBy()
}