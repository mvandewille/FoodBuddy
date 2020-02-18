package com.main.app.controller

import com.main.app.JSON.AllergenJ
import com.main.app.JSON.ResponseJ
import com.main.app.model.Allergen
import com.main.app.repository.AllergenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
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
    fun addName(@RequestBody allergen: AllergenJ): ResponseJ {
        try {
            val temp = repository.findByName(allergen.name)
            return ResponseJ(0, "Allergy already exists")
        }
        catch(e: EmptyResultDataAccessException) {
            repository.save(Allergen(allergen.name))
            return ResponseJ(1, "N/A")
        }
    }
    @GetMapping("/delete/all")
    fun findAll() :String {
        repository.deleteAll()
        return "All allergies deleted!"
    }

    fun stringify(list: MutableList<Allergen>): String {
        var r = ""
        list.forEach{
            r += it.toString() + "<br>"
        }
        return r
    }
}