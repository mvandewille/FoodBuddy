package com.main.fbBackend.Controller

import com.main.fbBackend.model.Status
import com.main.fbBackend.repository.StatusRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/status")
class StatusController {
    @Autowired
    lateinit var repository: StatusRepository

    @GetMapping("/add")
    fun addStatus(@RequestParam(value = "name", required = true) name: String,
                  @RequestParam(value = "message", required = true) message: String,
                  @RequestParam(value = "flagged", required = true) flagged: Boolean): String {
        val temp = Status(name, message, flagged)
        repository.save(temp)
        return "Added new $temp"
    }
}