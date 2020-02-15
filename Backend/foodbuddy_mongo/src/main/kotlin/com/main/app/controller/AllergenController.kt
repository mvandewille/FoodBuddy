package com.main.app.controller

import com.main.app.model.Allergen
import com.main.app.repository.AllergenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/allergen")
class AllergenController {
    @Autowired
    lateinit var repository: AllergenRepository

    @GetMapping("/add")
    fun addName(@RequestParam(value = "name", required = true) name: String): String{
        val t = Allergen(name)
        repository.save(t)

        return "Added new $t"
    }
    @GetMapping("/delete/all")
    fun findAll() :String {
        repository.deleteAll()
        return "All allergies deleted!"
    }
}