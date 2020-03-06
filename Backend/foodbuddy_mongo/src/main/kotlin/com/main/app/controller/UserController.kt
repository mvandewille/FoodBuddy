package com.main.app.controller

import com.main.app.JSON.*
import com.main.app.model.Food
import com.main.app.model.User
import com.main.app.repository.StatusRepository
import com.main.app.repository.UserRepository
import org.bson.internal.Base64

import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.RequestMapping
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var u_repository: UserRepository
    @Autowired
    lateinit var s_repository: StatusRepository

    @PostMapping("/image")
    fun decodeImage(@RequestBody image: TestJ): TestJ {
        val imageBytes: ByteArray = Base64.decode(image.img)
        //val image = ImageView
        return TestJ(imageBytes.toString())
    }

    @GetMapping("/auth")
    fun auth(@RequestParam(value = "email", required = true) email: String,
             @RequestParam(value = "password", required = true) password: String): ResponseJ {
        try {
            u_repository.findByEmailAndPassword(email, password)
            return ResponseJ(1, "N/A")
        }
        catch (e: EmptyResultDataAccessException) {
            return ResponseJ(0, "Login failed!")
        }
    }

    @GetMapping("/find/all")
    fun find(): UserJArray {
        try {
            var temp = u_repository.findAllBy()

            return UserJArray(temp.map { it.toJson() })
        }
        catch (e: EmptyResultDataAccessException) {
            return UserJArray(null)
        }
    }

    @GetMapping("/find/email")
    fun emailFind(@RequestParam(value= "email", required = true) email: String): UserJ {
        try {
            var temp = u_repository.findByEmail(email)
            return temp.toJson()
        }
        catch (e: EmptyResultDataAccessException) {
            return UserJ(email, null, null, null, null, null, null, null, null, mutableListOf<String>(), mutableListOf<String>(), mutableListOf<FoodJ>(), mutableMapOf<String, DayJ>())
        }
    }

    @GetMapping("/find/email/basic")
    fun emailFindBasic(@RequestParam(value= "email", required = true) email: String): UserBasicJ {
        try {
            var temp = u_repository.findByEmail(email)
            return temp.toBasicJson()
        }
        catch (e: EmptyResultDataAccessException) {
            return UserBasicJ(email, null, null, null, null, null, null, null, null, mutableListOf<String>())
        }
    }

    @GetMapping("/find/name")
    fun nameFind(@RequestParam(value = "name", required = true) name: String): UserJArray {
        try {
            var temp = u_repository.findByName(name)
            return UserJArray(temp.map { it.toJson() })
        }
        catch (e: EmptyResultDataAccessException) {
            return UserJArray(null)
        }
    }

    @GetMapping("/find/following/status")
    fun findFriendStatus(@RequestParam(value= "email", required = true) email: String): StatusJArray {
        try {
            val usr = u_repository.findByEmail(email)
            val following = usr.getFollowing()
            following.add(email)
            val temp = s_repository.findByEmailInOrderByIdDesc(following)
            return StatusJArray(temp.map { it.toJson() })
        }
        catch (e: EmptyResultDataAccessException) {
            return StatusJArray(null)
        }
    }

    @GetMapping("/day/total")
    fun getTotal(@RequestParam(value= "email", required = true) email: String, @RequestParam(value="date", required = false) r_date: String?): NutritionJ {
        try {
            val usr = u_repository.findByEmail(email)
            val result = NutritionJ(0, 0.0, 0.0, 0.0, 0.0, 0.0)
            val date: String
            if (r_date == null) {
                date = DateTimeFormatter
                    .ofPattern("MM/dd/yyyy")
                    .withZone(ZoneOffset.systemDefault())
                    .format(Instant.now())
            }
            else
                date = r_date


            val data = usr.getCalendar()
            if (data.keys.contains(date)) {
                data[date]?.getFoods()?.forEach {
                    val food = usr.getFood(it.getName())
                    val amt = it.getAmount()
                    if (food != null) {
                        result.calories += (food.getCalories() * amt).toInt()
                        result.sodium += food.getSodium() * amt
                        result.carbs += food.getCarbs() * amt
                        result.protein += food.getProtein() * amt
                        result.fat += food.getFat() * amt
                        result.cholesterol += food.getCholesterol() * amt
                    }
                }
            }
            return result
        }
        catch (e: EmptyResultDataAccessException){
            return NutritionJ(0, 0.0, 0.0, 0.0, 0.0, 0.0)
        }
    }

    @PostMapping("/add")
    fun addData(@RequestBody user: UserAddJ): ResponseJ {
        try{
            if(!"""^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$""".toRegex(RegexOption.IGNORE_CASE).matches(user.email))
                return ResponseJ(0, "Incorrect email format!")
            u_repository.findByEmail(user.email)
            return ResponseJ(0, "Email already connected to account!")
        }
        catch (e: EmptyResultDataAccessException) {
            if(user.password == null)
                return ResponseJ(0, "Password cannot be empty!")
            u_repository.save(User(user.email, user.password))
            return ResponseJ(1, "N/A")
        }
    }
/* this endpoint is going to be redundant in terms of how the app will be used. food will always be added to calendar and foods
    @PostMapping("/add/food")
    fun addFood(@RequestBody food: FoodAddJ): ResponseJ {
        try{
            var usr = u_repository.findByEmail(food.email)
            usr.getFoods().forEach { if(it.getName() == food.name) {return ResponseJ(0, "Food already exists for user!")} }
            if(usr.addFood(food.name, food.calories, food.sodium, food.carbs, food.protein, food.fat, food.cholesterol)) {
                u_repository.save(usr)
                return ResponseJ(1, "N/A")
            }
            else {
                return ResponseJ(0, "Error occurred!")
            }
        }
        catch(e: EmptyResultDataAccessException) {
            return ResponseJ(0, "No user found with that email!")
        }
    }
*/
    @PostMapping("/add/friend")
    fun addFriend(@RequestBody request: FriendJ): ResponseJ {
        try{
            u_repository.findByEmail(request.email)
        }
        catch (e: EmptyResultDataAccessException) {
            return ResponseJ(0, "No user found! Thus, no friends list!")
        }
        try{
            u_repository.findByEmail(request.friend)
            var usr = u_repository.findByEmail(request.email)

            usr.addFollowing(request.friend)
            u_repository.save(usr)
            return ResponseJ(1, "N/A")
        }
        catch (e: EmptyResultDataAccessException) {
            return ResponseJ(0, "This friend does not have an account!")
        }
    }

    @PostMapping("/day/add/food")
    fun addFoodToDay(@RequestBody food: FoodAddJ): ResponseJ {
        try{
            val usr = u_repository.findByEmail(food.email)
            val date = DateTimeFormatter
                    .ofPattern("MM/dd/yyyy")
                    .withZone(ZoneOffset.systemDefault())
                    .format(Instant.now())

            usr.addFood(Food(food.name, food.calories, food.sodium, food.carbs, food.protein, food.fat, food.cholesterol), food.amount, date)
            u_repository.save(usr)
            return ResponseJ(1, "N/A")
        }
        catch (e: EmptyResultDataAccessException) {
            return ResponseJ(0, "No User found!")
        }

    }

    @PostMapping("/update")
    fun updateUser(@RequestBody user: UserJ): ResponseJ {
        try{
            var temp = u_repository.findByEmail(user.email)
            if(temp.setExtras(user.name, user.age, user.height, user.weight, user.lifestyle, user.gender, user.calorieLimit, user.allergens)) {
                u_repository.save(temp)
                return ResponseJ(1, "N/A")
            }
            else {
                return ResponseJ(0, "Error occurred!")
            }
        }
        catch (e: EmptyResultDataAccessException) {
            return ResponseJ(0, "No user found to update!" )
        }
    }

    @GetMapping("/delete/email")
    fun delEmail(@RequestParam(value = "email", required = true) email: String): String {
        u_repository.deleteByEmail(email)
        return "$email deleted."
    }

    @GetMapping("/delete/all")
    fun delData(): String {
        u_repository.deleteAll()
        return "Success all rows removed!"
    }

}