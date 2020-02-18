package com.main.app.controller

import com.main.app.model.Food
import com.main.app.JSON.FoodJ
import com.main.app.JSON.ResponseJ
import com.main.app.repository.FoodRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.*

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
            return temp.toString()
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

    @PostMapping("/add")
    fun addFood(@RequestBody food: FoodJ): ResponseJ {
        try {
            val temp = repository.findByName(food.name)
            return ResponseJ(0, "Food already exists")
        }
        catch(e: EmptyResultDataAccessException) {
            repository.save(Food(food.name, food.calories, food.sodium, food.carbs, food.protein, food.fat, food.cholesterol))
            return ResponseJ(1, "N/A")
        }
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