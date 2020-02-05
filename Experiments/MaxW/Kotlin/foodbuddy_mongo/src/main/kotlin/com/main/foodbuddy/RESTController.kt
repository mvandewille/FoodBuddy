package com.main.foodbuddy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.dao.EmptyResultDataAccessException
import java.util.concurrent.atomic.AtomicLong

@RestController
class RESTController {
    @Autowired
    lateinit var repository: CustomerRepository

    //val maxId: Long
    lateinit var counter: AtomicLong

    @GetMapping("/add")
    fun addData(@RequestParam(value = "fName", required = true) fName: String, @RequestParam(value = "lName", required = true) lName: String): String{
        println("----------------------------------------------")
        val maxId: Long
        val newId: Long
        if (repository.count() != 0.toLong())
            maxId = repository.findByOrderByIdDesc()[0].id
        else
            maxId = -1
        newId = maxId + 1

        //val temp = Customer(fName, lName, counter.getAndIncrement())
        val temp = Customer(fName, lName, newId)
        repository.save(temp)

        return "Added new $temp"
    }

    @GetMapping("/delall")
    fun delData(): Int {
        repository.deleteAll()
        return 1
    }

    @GetMapping("/findID")
    fun idFind(@RequestParam(value = "num", defaultValue = "-1") num: String): String {
        try {
            var temp = repository.findById(num.toLong())
            return "Result: $temp"
        }
        catch (e: EmptyResultDataAccessException) {
            return "No matching entry!"
        }
    }
}