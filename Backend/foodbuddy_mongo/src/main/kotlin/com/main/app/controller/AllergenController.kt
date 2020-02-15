package com.main.app.controller

import com.main.app.JSON.AllergenJ
import com.main.app.model.Allergen
import com.main.app.repository.AllergenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/allergen")
class AllergenController {
    @Autowired
    lateinit var repository: AllergenRepository

    @PostMapping("/add")
    fun addName(@RequestBody allergen: AllergenJ): String{
        val t = Allergen(allergen.name)
        repository.save(t)

        return "Added new $t"
    }
    @GetMapping("/delete/all")
    fun findAll() :String {
        repository.deleteAll()
        return "All allergies deleted!"
    }
}