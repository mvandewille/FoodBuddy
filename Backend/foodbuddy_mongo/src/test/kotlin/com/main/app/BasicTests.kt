package com.main.app

import com.main.app.model.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasicTests {
    //STATUS CLASS TESTS

    //Tests the status check date
    @Test
    fun testStatusCheckDate()
    {
        //Mock the object
        val s = Mockito.mock(Status::class.java)
        //Call
        s.checkDate("Test")
        //verify it is called
        Mockito.verify(s).checkDate("Test")
    }

    //Test the status to Json method
    @Test
    fun testStatusToJson()
    {
        //Mock the object
        val s = Mockito.mock(Status::class.java)
        //Call
        s.toJson()
        //verify if is called
        Mockito.verify(s).toJson()
    }

    //TEST THE USER CLASS
    //Test the Get Foods method
    @Test
    fun testUserGetFoods()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.getFoods()
        //verify if is called
        Mockito.verify(u).getFoods()
    }

    @Test
    //Test the get food method
    fun testUserGetFood()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.getFood("Apple")
        //verify if is called
        Mockito.verify(u).getFood("Apple")
    }

    @Test
    //Tests the get Calender method
    fun testUserGetCalender()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.getCalendar()
        //verify if is called
        Mockito.verify(u).getCalendar()
    }

    //Tests the get following method
    @Test
    fun testUserGetFollowing()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.getFollowing()
        //verify if is called
        Mockito.verify(u).getFollowing()
    }

    @Test
    fun testUserToJson()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.toJson()
        //verify if is called
        Mockito.verify(u).toJson()
    }

    @Test
    fun testUserToBasicJson()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.toBasicJson()
        //verify if is called
        Mockito.verify(u).toBasicJson()
    }

    @Test
    fun testUserAddFollowing()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.addFollowing("Test")
        //verify if is called
        Mockito.verify(u).addFollowing("Test")
    }

    @Test
    fun testUserSetExtras()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        val temp = mutableListOf<String>()
        u.setExtras("Text", 1, 1,
                1, "Fat", "M", 1, temp)
        //verify if is called
        Mockito.verify(u).setExtras("Text", 1, 1,
                1, "Fat", "M", 1, temp)
    }

    //Uses a mock of Food as well
    @Test
    fun testUserAddFood()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        val f = Mockito.mock(Food::class.java)
        u.addFood(f, 1.0,"Test")
        //verify if is called
        Mockito.verify(u).addFood(f, 1.0, "Test")
    }

    @Test
    fun testUserCheckDate()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.checkDateExists("Test")
        //verify if is called
        Mockito.verify(u).checkDateExists("Test")
    }

    @Test
    fun testUserChangePassword()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.changePass("Test")
        //verify if is called
        Mockito.verify(u).changePass("Test")
    }

    //TESTS FOR FOOD CLASS
    @Test
    fun testFoodGetName()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getName()
        //verify if is called
        Mockito.verify(f).getName()
    }

    @Test
    fun testFoodGetCalories()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getCalories()
        //verify if is called
        Mockito.verify(f).getCalories()
    }

    @Test
    fun testFoodGetSodium()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getSodium()
        //verify if is called
        Mockito.verify(f).getSodium()
    }

    @Test
    fun testFoodGetCarbs()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getCarbs()
        //verify if is called
        Mockito.verify(f).getCarbs()
    }

    @Test
    fun testFoodGetProtein()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getProtein()
        //verify if is called
        Mockito.verify(f).getProtein()
    }

    @Test
    fun testFoodGetFat()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getFat()
        //verify if is called
        Mockito.verify(f).getFat()
    }

    @Test
    fun testFoodGetCholeserol()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getCholesterol()
        //verify if is called
        Mockito.verify(f).getCholesterol()
    }

    @Test
    fun testFoodToJson()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.toJson()
        //verify if is called
        Mockito.verify(f).toJson()
    }

    //TEST DAYFOOD CLASS
    @Test
    fun testDayFoodGetName()
    {
        //Mock the object
        val f = Mockito.mock(DayFood::class.java)
        //Call
        f.getName()
        //verify if is called
        Mockito.verify(f).getName()
    }

    @Test
    fun testDayFoodGetAmount()
    {
        //Mock the object
        val f = Mockito.mock(DayFood::class.java)
        //Call
        f.getAmount()
        //verify if is called
        Mockito.verify(f).getAmount()
    }

    @Test
    fun testDayFoodToJson()
    {
        //Mock the object
        val f = Mockito.mock(DayFood::class.java)
        //Call
        f.toJson()
        //verify if is called
        Mockito.verify(f).toJson()
    }

    //TEST THE DAY CLASS
    @Test
    fun testDayGetFoods()
    {
        //Mock the object
        val f = Mockito.mock(Day::class.java)
        //Call
        f.getFoods()
        //verify if is called
        Mockito.verify(f).getFoods()
    }

    @Test
    fun testDayAddFoods()
    {
        //Mock the object
        val f = Mockito.mock(Day::class.java)
        //Call
        f.addFood("Test", 1.0)
        //verify if is called
        Mockito.verify(f).addFood("Test", 1.0)
    }

    @Test
    fun testDayToJson()
    {
        //Mock the object
        val f = Mockito.mock(Day::class.java)
        //Call
        f.toJson()
        //verify if is called
        Mockito.verify(f).toJson()
    }
}