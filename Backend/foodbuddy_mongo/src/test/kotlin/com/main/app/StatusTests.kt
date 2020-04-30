package com.main.app

import com.main.app.model.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatusTests {
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
}