package com.main.app

import com.main.app.model.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DayTests {
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