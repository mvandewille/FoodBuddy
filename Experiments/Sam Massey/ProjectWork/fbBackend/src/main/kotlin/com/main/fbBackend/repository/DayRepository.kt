package com.main.fbBackend.repository

import com.main.fbBackend.model.Day
import kotlin.collections.MutableList
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface DayRepository : MongoRepository<Day, String> {
    fun findAllBy(): MutableList<Day>
    fun findByDate(date: Date): MutableList<Day>

    fun deleteByDate(date: Date): Day
}