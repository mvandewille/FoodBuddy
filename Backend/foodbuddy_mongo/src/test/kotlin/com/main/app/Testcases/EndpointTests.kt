package com.main.app.Testcases

import com.main.app.controller.StatusController
import com.main.app.repository.StatusRepository
import com.nhaarman.mockitokotlin2.*;
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

//@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class EndpointTests {

    @Test
    fun test_Status()
    {

    }
}