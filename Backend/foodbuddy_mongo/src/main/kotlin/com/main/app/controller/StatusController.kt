package com.main.app.controller

import com.main.app.JSON.ResponseJ
import com.main.app.JSON.StatusJ
import com.main.app.JSON.StatusJArray
import com.main.app.model.Status
import com.main.app.repository.StatusRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/status")
class StatusController {
    @Autowired
    lateinit var repository: StatusRepository

    @GetMapping("/find/all")
    fun find(): StatusJArray {
        try {
            var temp = repository.findAllBy()

            return StatusJArray(temp.map { it.toJson() })
        }
        catch (e: EmptyResultDataAccessException) {
            return StatusJArray(null)
        }
    }

    @PostMapping("/add")
    fun addStatus(@RequestBody status: StatusJ): ResponseJ {
        if(status.email == null || status.message == null)
            return ResponseJ(0, "Null value for email or message!")
        val list = repository.findAllByOrderByIdDesc()
        if(!list.isEmpty()) {
            val temp = list.first().getId()
            repository.save(Status(temp+1, status.email, status.message))
            return ResponseJ(1, "N/A")
        }
        else {
            repository.save(Status(0, status.email, status.message))
            return ResponseJ(1, "N/A")
        }
    }

    @GetMapping("/update/flag")
    fun updateStatusFlag(@RequestParam(value = "flagged", required = true) flagged: Boolean)
    {
        repository.findByMessageOrderById().first()
    }

    @DeleteMapping("/delete/all")
    fun deleteAll(): ResponseJ {
        repository.deleteAll()
        return ResponseJ(1, "All statuses deleted")
    }

    @GetMapping("/count")
    fun getCount(): Long {
        return repository.count()
    }

}