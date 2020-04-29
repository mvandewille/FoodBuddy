package com.main.app

import com.main.app.model.*

import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.slf4j.LoggerFactory


@ExtendWith(SpringExtension::class)
//@ContextConfiguration(classes = {WebConfig::class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FoodbuddyApplicationTests {

    @Autowired
    lateinit var mvc: MockMvc

    private val logger = LoggerFactory.getLogger(FoodbuddyApplicationTests::class.java)

    @Test
    fun testAddUser() {
        //mvc.perform(post("/user/find/all")).andExpect(status().isOk)
        val result: MvcResult = mvc.perform(post("/user/add").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk)
                .andReturn()
        logger.info(result.response.contentAsString)
    }
    //STATUS CLASS TESTS

    //Tests the status check date
    @Test
    fun testStatusCheckDate()
    {
        //Mock the object
        val s = mock(Status::class.java)
        //Call
        s.checkDate("Test")
        //verify it is called
        verify(s).checkDate("Test")
    }

    //Test the status to Json method
    @Test
    fun testStatusToJson()
    {
        //Mock the object
        val s = mock(Status::class.java)
        //Call
        s.toJson()
        //verify if is called
        verify(s).toJson()
    }

    //TEST THE USER CLASS
    //Test the Get Foods method
    @Test
    fun testUserGetFoods()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.getFoods()
        //verify if is called
        verify(u).getFoods()
    }

    @Test
    //Test the get food method
    fun testUserGetFood()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.getFood("Apple")
        //verify if is called
        verify(u).getFood("Apple")
    }

    @Test
    //Tests the get Calender method
    fun testUserGetCalender()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.getCalendar()
        //verify if is called
        verify(u).getCalendar()
    }

    //Tests the get following method
    @Test
    fun testUserGetFollowing()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.getFollowing()
        //verify if is called
        verify(u).getFollowing()
    }

    @Test
    fun testUserToJson()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.toJson()
        //verify if is called
        verify(u).toJson()
    }

    @Test
    fun testUserToBasicJson()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.toBasicJson()
        //verify if is called
        verify(u).toBasicJson()
    }

    @Test
    fun testUserAddFollowing()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.addFollowing("Test")
        //verify if is called
        verify(u).addFollowing("Test")
    }

    @Test
    fun testUserSetExtras()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        val temp = mutableListOf<String>()
        u.setExtras("Text", 1, 1,
                1, "Fat", "M", 1, temp)
        //verify if is called
        verify(u).setExtras("Text", 1, 1,
                1, "Fat", "M", 1, temp)
    }

    //Uses a mock of Food as well
    @Test
    fun testUserAddFood()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        val f = mock(Food::class.java)
        u.addFood(f, 1.0,"Test")
        //verify if is called
        verify(u).addFood(f, 1.0, "Test")
    }

    @Test
    fun testUserCheckDate()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.checkDateExists("Test")
        //verify if is called
        verify(u).checkDateExists("Test")
    }

    @Test
    fun testUserChangePassword()
    {
        //Mock the object
        val u = mock(User::class.java)
        //Call
        u.changePass("Test")
        //verify if is called
        verify(u).changePass("Test")
    }

    //TESTS FOR FOOD CLASS
    @Test
    fun testFoodGetName()
    {
        //Mock the object
        val f = mock(Food::class.java)
        //Call
        f.getName()
        //verify if is called
        verify(f).getName()
    }

    @Test
    fun testFoodGetCalories()
    {
        //Mock the object
        val f = mock(Food::class.java)
        //Call
        f.getCalories()
        //verify if is called
        verify(f).getCalories()
    }

    @Test
    fun testFoodGetSodium()
    {
        //Mock the object
        val f = mock(Food::class.java)
        //Call
        f.getSodium()
        //verify if is called
        verify(f).getSodium()
    }

    @Test
    fun testFoodGetCarbs()
    {
        //Mock the object
        val f = mock(Food::class.java)
        //Call
        f.getCarbs()
        //verify if is called
        verify(f).getCarbs()
    }

    @Test
    fun testFoodGetProtein()
    {
        //Mock the object
        val f = mock(Food::class.java)
        //Call
        f.getProtein()
        //verify if is called
        verify(f).getProtein()
    }

    @Test
    fun testFoodGetFat()
    {
        //Mock the object
        val f = mock(Food::class.java)
        //Call
        f.getFat()
        //verify if is called
        verify(f).getFat()
    }

    @Test
    fun testFoodGetCholeserol()
    {
        //Mock the object
        val f = mock(Food::class.java)
        //Call
        f.getCholesterol()
        //verify if is called
        verify(f).getCholesterol()
    }

    @Test
    fun testFoodToJson()
    {
        //Mock the object
        val f = mock(Food::class.java)
        //Call
        f.toJson()
        //verify if is called
        verify(f).toJson()
    }

    //TEST DAYFOOD CLASS
    @Test
    fun testDayFoodGetName()
    {
        //Mock the object
        val f = mock(DayFood::class.java)
        //Call
        f.getName()
        //verify if is called
        verify(f).getName()
    }

    @Test
    fun testDayFoodGetAmount()
    {
        //Mock the object
        val f = mock(DayFood::class.java)
        //Call
        f.getAmount()
        //verify if is called
        verify(f).getAmount()
    }

    @Test
    fun testDayFoodToJson()
    {
        //Mock the object
        val f = mock(DayFood::class.java)
        //Call
        f.toJson()
        //verify if is called
        verify(f).toJson()
    }

    //TEST THE DAY CLASS
    @Test
    fun testDayGetFoods()
    {
        //Mock the object
        val f = mock(Day::class.java)
        //Call
        f.getFoods()
        //verify if is called
        verify(f).getFoods()
    }

    @Test
    fun testDayAddFoods()
    {
        //Mock the object
        val f = mock(Day::class.java)
        //Call
        f.addFood("Test", 1.0)
        //verify if is called
        verify(f).addFood("Test", 1.0)
    }

    @Test
    fun testDayToJson()
    {
        //Mock the object
        val f = mock(Day::class.java)
        //Call
        f.toJson()
        //verify if is called
        verify(f).toJson()
    }
}

