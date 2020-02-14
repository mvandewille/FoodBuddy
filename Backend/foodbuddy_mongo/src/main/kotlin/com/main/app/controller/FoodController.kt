package com.main.app.controller

import com.main.app.model.Food
import com.main.app.repository.FoodRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/food")
class FoodController {
    @Autowired
    lateinit var repository: FoodRepository

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

    @GetMapping("/find/name")
    fun nameFind(@RequestParam(value = "name", required = true) name: String): String {
        try {
            var temp = repository.findByName(name)
            return stringify(temp)
        }
        catch (e: EmptyResultDataAccessException) {
            return "No matching entry!"
        }
    }

    @GetMapping("/find/calorie")
    fun findCalorie(): String {
        try {
            var temp = repository.findByOrderByCaloriesAsc()

            return stringify(temp)
        }
        catch (e: EmptyResultDataAccessException) {
            return "No data!"
        }
    }

    @GetMapping("/add")
    fun addFood(@RequestParam(value = "name", required = true) name: String,
                @RequestParam(value = "calories", defaultValue = "0") calories: String): String {
        val temp = Food(name, calories.toInt())
        repository.save(temp)
        return "Added new $temp"
    }

    @GetMapping("/delete/name")
    fun delName(@RequestParam(value = "name", required = true) name: String): String {
        var temp = repository.deleteByName(name)
        return "Deleted $temp"
    }

    @GetMapping("/delete/all")
    fun delData(): String {
        repository.deleteAll()
        return "Success all rows removed"
    }

    fun stringify(list: MutableList<Food>): String {
        var r = ""
        list.forEach{
            r += it.toString() + "<br>"
        }
        return r
    }
}