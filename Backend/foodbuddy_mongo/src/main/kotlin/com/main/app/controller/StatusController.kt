package com.main.app.controller

import com.main.app.JSON.ResponseJ
import com.main.app.JSON.StatusJ
import com.main.app.model.Status
import com.main.app.repository.StatusRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/status")
class StatusController {
    @Autowired
    lateinit var repository: StatusRepository

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
    fun addStatus(@RequestBody status: StatusJ): ResponseJ {
        val list = repository.findAllByOrderByIdDesc()
        if(!list.isEmpty()) {
            val temp = list.first().getId()
            repository.save(Status(temp+1, status.name, status.message))
            return ResponseJ(1, "N/A")
        }
        else {
            repository.save(Status(0, status.name, status.message))
            return ResponseJ(1, "N/A")
        }
    }

    @GetMapping("/update/flag")
    fun updateStatusFlag(@RequestParam(value = "flagged", required = true) flagged: Boolean)
    {
        val temp = repository.findByMessageOrderById().first()
    }

    @GetMapping("/delete/all")
    fun deleteAll(): String {
        repository.deleteAll()
        return "Deleted Everything!"
    }

    @GetMapping("/count")
    fun getCount(): Long {
        return repository.count()
    }

    fun stringify(list: MutableList<Status>): String {
        var r = ""
        list.forEach{
            r += it.toString() + "<br>"
        }
        return r
    }
}