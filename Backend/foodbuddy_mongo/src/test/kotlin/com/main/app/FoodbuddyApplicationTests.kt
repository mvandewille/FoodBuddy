package com.main.app

import com.main.app.json.StatusJ
import com.main.app.model.Status
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
}
