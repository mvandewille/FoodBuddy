package com.main.app.repository

import com.main.app.model.Status
import kotlin.collections.MutableList
import org.springframework.data.mongodb.repository.MongoRepository

interface StatusRepository : MongoRepository<Status, String>{

    fun findAllBy() : MutableList<Status>
    fun findById(id: Long) : Status
    fun findAllByOrderByIdDesc() : MutableList<Status>
    fun findByName(name : String) : MutableList<Status>
    fun findByMessage(message : String) : MutableList<Status>
    fun findByFlagged(flagged : Boolean) : MutableList<Status>
    fun findByMessageOrderById() : MutableList<Status>
}