package com.main.app.controller

import com.main.app.JSON.ResponseJ
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

    @GetMapping("/auth")
    fun auth(@RequestParam(value = "email", required = true) email: String,
             @RequestParam(value = "password", required = true) password: String): ResponseJ {
        try {
            var temp = repository.findByEmailAndPassword(email, password)
            if(temp != null)
                return ResponseJ(1, "N/A")
            else
                return ResponseJ(0, "Login failed!")
        }
        catch (e: EmptyResultDataAccessException) {
            return ResponseJ(0, "Login failed!")
        }
    }

    @GetMapping("/find/all")
    fun find(): UserJArray {
        try {
            var temp = repository.findAllBy()

            return UserJArray(temp.map {it.toJson()})
        }
        catch (e: EmptyResultDataAccessException) {
            return UserJArray(null)
        }
    }

    @GetMapping("/find/email")
    fun emailFind(@RequestParam(value= "email", required = true) email: String): UserJ {
        try {
            var temp = repository.findByEmail(email)
            return temp.toJson()
        }
        // This is really poorly made right now we need to adjust it
        catch (e: EmptyResultDataAccessException) {
            return UserJ(email, null, null, null, null, null, null, null, null, null)
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

    @PostMapping("/add")
    fun addData(@RequestBody user: UserJ): ResponseJ {
        try{
            if(!"""^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$""".toRegex(RegexOption.IGNORE_CASE).matches(user.email))
                return ResponseJ(0, "Incorrect email format!")
            repository.findByEmail(user.email)
            return ResponseJ(0, "Email already connected to account!")
        }
        catch (e: EmptyResultDataAccessException) {
            if(user.password == null)
                return ResponseJ(0, "Password cannot be empty!")
            repository.save(User(user.email, user.password))
            return ResponseJ(1, "N/A")
        }
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody user: UserJ): ResponseJ {
        try{
            var temp = repository.findByEmail(user.email)
            temp.setExtras(user.name, user.height, user.weight, user.lifestyle, user.gender, user.allergens)
            repository.save(temp)
            return ResponseJ(1, "N/A")
        }
        catch (e: EmptyResultDataAccessException) {
            return ResponseJ(0, "No user found to update!" )
        }
    }

    @GetMapping("/delete/email")
    fun delEmail(@RequestParam(value = "email", required = true) email: String): String {
        repository.deleteByEmail(email)
        return "$email deleted."
    }

    @GetMapping("/delete/all")
    fun delData(): String {
        repository.deleteAll()
        return "Success all rows removed!"
    }

    fun stringify(list: MutableList<User>): String {
        var r = ""
        list.forEach{
            r += it.toString() + "<br>"
        }
        return r
    }

}