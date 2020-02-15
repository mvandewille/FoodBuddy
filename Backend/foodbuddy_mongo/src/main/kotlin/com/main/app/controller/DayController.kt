package com.main.fbBackend.Controller


import com.main.fbBackend.model.Allergen
import com.main.fbBackend.model.Day
import com.main.fbBackend.repository.AllergenRepository
import com.main.fbBackend.repository.DayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*


@RestController
@RequestMapping("/day")
class DayController {
    @Autowired
    lateinit var repository: DayRepository

    @GetMapping("/add")
    fun addDay(@RequestParam(value = "date", required = true) date: Date): String{
        val t = Day(date)
        repository.save(t)

        return "Added new $t"
    }
    @GetMapping("/delete/all")
    fun findAll() :String {
        repository.deleteAll()
        return "All dates deleted!"
    }
}