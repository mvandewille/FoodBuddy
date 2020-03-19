package com.main.app.controller

import com.main.app.json.ResponseJ
import com.main.app.json.StatusJ
import com.main.app.json.StatusJArray
import com.main.app.json.StatusLikeJ
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

    @PostMapping("/add/like")
    fun addLike(@RequestBody status: StatusLikeJ): ResponseJ {
        try {
            val selected = repository.findById(status.id)
            selected.addLike()

            repository.deleteById(status.id)
            repository.save(selected)
            return ResponseJ(1, "N/A")
        }
        catch (e: EmptyResultDataAccessException) {
            return ResponseJ(0, "No status with that id!")
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