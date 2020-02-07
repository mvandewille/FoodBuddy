package com.main.app.repository

import com.main.app.model.User
import kotlin.collections.MutableList
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findAllBy(): MutableList<User>
    fun findByEmail(email: String): User
    fun findByName(name: String): MutableList<User>

    //Possible login query?
    fun findByEmailAndPassword(email: String, password: String): MutableList<User>

    fun findByOrderByUserTypeAsc(): MutableList<User>
    fun findByOrderByUserTypeDesc(): MutableList<User>

    //Order users by height and weight
    fun findByOrderByHeightAsc(): MutableList<User>
    fun findByOrderByHeightDesc(): MutableList<User>

    fun findByOrderByWeightAsc(): MutableList<User>
    fun findByOrderByWeightDesc(): MutableList<User>

    fun deleteByEmail(email: String)
}