package com.main.app

import com.main.app.json.StatusJ
import com.main.app.model.Status
import com.main.app.model.User
import org.hamcrest.MatcherAssert.assertThat
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
        
    }
}
