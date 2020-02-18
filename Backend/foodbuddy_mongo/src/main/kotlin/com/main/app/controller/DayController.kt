package com.main.app.controller


import com.main.app.JSON.DayJ
import com.main.app.JSON.ResponseJ
import com.main.app.model.Day
import com.main.app.repository.DayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@RestController
@RequestMapping("/day")
class DayController {
    @Autowired
    lateinit var repository: DayRepository

    @GetMapping("/find/all")
    fun find(): String {
        try {
            var temp = repository.findAllBy()

            return stringify(temp)
        }
        catch (e: EmptyResultDataAccessException) {
            return "No data!"
        }
    }

    @PostMapping("/add")
    fun addName(@RequestBody day: DayJ): ResponseJ {
        if(checkDate(day.date)) {
            try {
                val temp = repository.findByDate(day.date)
                return ResponseJ(0, "Day already exists")
            } catch (e: EmptyResultDataAccessException) {
                repository.save(Day(day.date))
                return ResponseJ(1, "N/A")
            }
        }
        else
            return ResponseJ(0, "Incorrect date format. Correct format is mm/dd/yyyy")
    }

    @GetMapping("/delete/all")
    fun findAll() :String {
        repository.deleteAll()
        return "All dates deleted!"
    }

    fun stringify(list: MutableList<Day>): String {
        var r = ""
        list.forEach{
            r += it.toString() + "<br>"
        }
        return r
    }

    //date format should be MM/dd/yyyy
    fun checkDate(date: String): Boolean {
        return """^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d${'$'}""".toRegex().matches(date)
    }
}