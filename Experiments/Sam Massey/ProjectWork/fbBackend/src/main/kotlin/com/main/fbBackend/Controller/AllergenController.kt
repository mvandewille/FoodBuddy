package com.main.fbBackend.Controller

import com.main.fbBackend.model.Allergen
import com.main.fbBackend.repository.AllergenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.dao.EmptyResultDataAccessException
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
}