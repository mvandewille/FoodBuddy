package com.main.app.controller

import com.main.app.model.User
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

    @GetMapping("/find")
    fun find(): String {
        try {
            var temp = repository.findAllBy()
            var result = ""

            temp.forEach{
                result += it.toString() + "<br>"
            }
            return result
        }
        catch (e: EmptyResultDataAccessException) {
            return "No data!"
        }
    }

    @GetMapping("/findEmail")
    fun idFind(@RequestParam(value = "email", defaultValue = "-1") email: String): String {
        try {
            var temp = repository.findByEmail(email)
            return "Result: $temp"
        }
        catch (e: EmptyResultDataAccessException) {
            return "No matching entry!"
        }
    }

    @GetMapping("/add")
    fun addData(
            @RequestParam(value = "email", required = true) email: String,
            @RequestParam(value = "name", required = true) name: String,
            @RequestParam(value = "password", required = true) password: String,
            @RequestParam(value = "userType", required = true) userType: String): String{
        val temp = User(email, name, password, userType)
        repository.save(temp)

        return "Added new $temp"
    }

    @GetMapping("/delall")
    fun delData(): String {
        repository.deleteAll()
        return "Success all rows removed"
    }

}