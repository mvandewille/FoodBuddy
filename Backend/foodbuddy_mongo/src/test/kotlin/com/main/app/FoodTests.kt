package com.main.app

import com.main.app.model.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FoodTests {
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
}