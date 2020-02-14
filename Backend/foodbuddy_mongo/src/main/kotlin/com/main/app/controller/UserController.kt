package com.main.app.controller

import com.main.app.model.User
import com.main.app.repository.UserRepository
import com.main.app.JSON.UserJ

import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var repository: UserRepository

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

    @GetMapping("/find/email")
    fun emailFind(@RequestParam(value = "email", required = true) email: String): String {
        try {
            var temp = repository.findByEmail(email)
            return "Result: $temp"
        }
        catch (e: EmptyResultDataAccessException) {
            return "No matching entry!"
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
    /*
    @PostMapping("/add")
    fun addData(
            @RequestParam(value = "email", required = true) email: String,
            @RequestParam(value = "name", required = true) name: String,
            @RequestParam(value = "password", required = true) password: String): String{
        val temp = User(email, name, password)
        repository.save(temp)

        return "Added new $temp"
    }
*/
    @PostMapping("/add")
    fun addData(@RequestBody user: UserJ): String {
        val temp = User(user.email, user.name, user.password)
        repository.save(temp)

        return "Added new $temp"
    }
    @GetMapping("/delete/email")
    fun delEmail(@RequestParam(value = "email", required = true) email: String): String {
        repository.deleteByEmail(email)
        return "$email deleted."
    }

    @GetMapping("/delete/all")
    fun delData(): String {
        repository.deleteAll()
        return "Success all rows removed"
    }

    fun stringify(list: MutableList<User>): String {
        var r = ""
        list.forEach{
            r += it.toString() + "<br>"
        }
        return r
    }

}