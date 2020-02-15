package com.main.fbBackend.repository

import com.main.fbBackend.model.Status
import kotlin.collections.MutableList
import org.springframework.data.mongodb.repository.MongoRepository

interface StatusRepository : MongoRepository<Status, String>{

    fun findAllBy() : MutableList<Status>
    fun findByName(name : String) : MutableList<Status>
    fun findByMessage(message : String) : MutableList<Status>
    fun findByFlagged(flagged : Boolean) : MutableList<Status>
    fun findByStatusIDOrderByStatusIDDesc() : MutableList<Status>
}