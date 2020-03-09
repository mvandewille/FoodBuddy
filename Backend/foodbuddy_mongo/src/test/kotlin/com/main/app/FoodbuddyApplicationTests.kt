package com.main.app

import com.main.app.model.DayFood
import com.main.app.model.Food
import com.main.app.model.Status
import com.main.app.model.User
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FoodbuddyApplicationTests {

    //STATUS CLASS TESTS
    @Test
    fun testStatusGetId()
    {
        //Mock the object
        val s = Mockito.mock(Status::class.java)
        //Call
        s.getId()
        //verify it is called
        verify(s).getId()
    }

    @Test
    fun TestStatusCheckDate()
    {
        //Mock the object
        val s = Mockito.mock(Status::class.java)
        //Call
        s.checkDate("Test")
        //verify it is called
        verify(s).checkDate("Test")
    }

    @Test
    fun TestStatusToString()
    {
        //Mock the object
        val s = Mockito.mock(Status::class.java)
        //Call
        s.toJson()
        //verify if is called
        verify(s).toJson()
    }

    //TEST THE USER CLASS
    @Test
    fun TestUserGetFoods()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.getFoods()
        //verify if is called
        verify(u).getFoods()
    }

    @Test
    fun TestUserGetFood()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.getFood("Apple")
        //verify if is called
        verify(u).getFood("Apple")
    }

    @Test
    fun TestUserGetCalender()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.getCalendar()
        //verify if is called
        verify(u).getCalendar()
    }

    @Test
    fun TestUserGetFollowing()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.getFollowing()
        //verify if is called
        verify(u).getFollowing()
    }

    @Test
    fun TestUserToJson()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.toJson()
        //verify if is called
        verify(u).toJson()
    }

    @Test
    fun TestUserToBasicJson()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.toBasicJson()
        //verify if is called
        verify(u).toBasicJson()
    }

    @Test
    fun TestUserAddFollowing()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.addFollowing("Test")
        //verify if is called
        verify(u).addFollowing("Test")
    }

    @Test
    fun TestUserSetExtras()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
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
    fun TestUserAddFood()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        val f = Mockito.mock(Food::class.java)
        u.addFood(f, 1.0,"Test")
        //verify if is called
        verify(u).addFood(f, 1.0, "Test")
    }

    @Test
    fun TestUserCheckDate()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.checkDateExists("Test")
        //verify if is called
        verify(u).checkDateExists("Test")
    }

    @Test
    fun TestUserChangePassword()
    {
        //Mock the object
        val u = Mockito.mock(User::class.java)
        //Call
        u.changePass("Test")
        //verify if is called
        verify(u).changePass("Test")
    }

    //TESTS FOR FOOD CLASS
    @Test
    fun TestFoodGetName()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getName()
        //verify if is called
        verify(f).getName()
    }

    @Test
    fun TestFoodGetCalories()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getCalories()
        //verify if is called
        verify(f).getCalories()
    }

    @Test
    fun TestFoodGetSodium()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getSodium()
        //verify if is called
        verify(f).getSodium()
    }

    @Test
    fun TestFoodGetCarbs()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getCarbs()
        //verify if is called
        verify(f).getCarbs()
    }

    @Test
    fun TestFoodGetProtein()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getProtein()
        //verify if is called
        verify(f).getProtein()
    }

    @Test
    fun TestFoodGetFat()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getFat()
        //verify if is called
        verify(f).getFat()
    }

    @Test
    fun TestFoodGetCholeserol()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.getCholesterol()
        //verify if is called
        verify(f).getCholesterol()
    }

    @Test
    fun TestFoodToJson()
    {
        //Mock the object
        val f = Mockito.mock(Food::class.java)
        //Call
        f.toJson()
        //verify if is called
        verify(f).toJson()
    }

    //TEST DAYFOOD CLASS
    @Test
    fun TestDayFoodGetName()
    {
        //Mock the object
        val f = Mockito.mock(DayFood::class.java)
        //Call
        f.getName()
        //verify if is called
        verify(f).getName()
    }

    @Test
    fun TestDayFoodGetAmount()
    {
        //Mock the object
        val f = Mockito.mock(DayFood::class.java)
        //Call
        f.getAmount()
        //verify if is called
        verify(f).getAmount()
    }

    @Test
    fun TestDayFoodToJson()
    {
        //Mock the object
        val f = Mockito.mock(DayFood::class.java)
        //Call
        f.toJson()
        //verify if is called
        verify(f).toJson()
    }
}
