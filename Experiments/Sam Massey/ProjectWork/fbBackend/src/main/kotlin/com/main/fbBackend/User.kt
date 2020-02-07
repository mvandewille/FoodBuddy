package com.main.fbBackend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.dao.EmptyResultDataAccessException
import java.util.concurrent.atomic.AtomicLong

@RestController
class UserController{
    @GetMapping("/")
    fun getUser() = "User 1"
}