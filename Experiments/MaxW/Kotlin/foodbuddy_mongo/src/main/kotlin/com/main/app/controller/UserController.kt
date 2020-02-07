package com.main.app.controller

import com.main.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var repository: UserRepository

    @GetMapping("/delall")
    fun delData(): Int {
        repository.deleteAll()
        return 1
    }

    @GetMapping("/user/findEmail")
    fun idFind(@RequestParam(value = "email", defaultValue = "-1") email: String): String {
        try {
            var temp = repository.findByEmail(email)
            return "Result: $temp"
        }
        catch (e: EmptyResultDataAccessException) {
            return "No matching entry!"
        }
    }
}