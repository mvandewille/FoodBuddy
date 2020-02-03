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

    var counter = AtomicLong()

    @GetMapping("/add")
    fun addData(@RequestParam(value = "fName", defaultValue = " ") fName: String, @RequestParam(value = "lName", defaultValue = " ") lName: String): String{
        if (!(fName == " " || lName == " ")) {
            var temp = Customer(fName, lName, counter.incrementAndGet())
            repository.save(temp)

            return "Added new $temp"
        }
        return "Err: Firstname: $fName    Lastname: $lName"
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