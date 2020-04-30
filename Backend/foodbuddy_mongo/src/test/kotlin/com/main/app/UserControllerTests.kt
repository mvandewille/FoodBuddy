package com.main.app

import com.main.app.json.UserAddJ

import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.fasterxml.jackson.databind.ObjectMapper
import com.main.app.json.FoodAddJ
import com.main.app.json.FriendJ
import com.main.app.json.UserBasicJ
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    private val mapper = ObjectMapper()

    @BeforeAll
    fun setup() {
        val json = mapper.writeValueAsString(UserAddJ("testmockaccount@test.com", "password"))
        val json2 = mapper.writeValueAsString(UserAddJ("testmockfriend@test.com", "password"))
        mvc.perform(post("/user/add").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
        mvc.perform(post("/user/add").contentType(MediaType.APPLICATION_JSON)
                .content(json2))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
    }

    @Test
    fun testAuthUser() {
        mvc.perform(get("/user/auth").param("email", "testmockaccount@test.com")
                .param("password", "password"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
    }

    @Test
    fun testFindAll() {
        mvc.perform(get("/user/find/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
    }

    @Test
    fun testFindEmail() {
        mvc.perform(get("/user/find/email").param("email", "testmockaccount@test.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.email", `is`(notNullValue())))
    }

    @Test
    fun testFindEmailBasic() {
        mvc.perform(get("/user/find/email/basic").param("email", "testmockaccount@test.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.email", `is`(notNullValue())))
    }

    @Test
    fun testUserUpdate() {
        val json = mapper.writeValueAsString(UserBasicJ("testmockaccount@test.com", "Max", 1, 10, 10, "Active", "Computer", 1, "tester", mutableListOf()))
        mvc.perform(post("/user/update").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
    }

    @Test
    fun testUserUpdatePassword() {
        val json = mapper.writeValueAsString(UserAddJ("testmockaccount@test.com", "password"))
        mvc.perform(post("/user/update/password").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
    }

    @Test
    fun testUserAddFood() {
        val json = mapper.writeValueAsString(FoodAddJ("testmockaccount@test.com", "Sustenance", 1000, 15.toDouble(), 15.toDouble(), 15.toDouble(), 15.toDouble(), 15.toDouble(), 2.toDouble()))
        mvc.perform(post("/user/day/add/food").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
    }

    @Test
    fun testAddFollowing() {
        val json = mapper.writeValueAsString(FriendJ("testmockaccount@test.com", "testmockfriend@test.com"))
        mvc.perform(post("/user/add/following").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
    }

    @Test
    fun testFindFollowing() {
        mvc.perform(get("/user/find/following").param("email", "testmockaccount@test.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.content", `is`(notNullValue())))
    }

    @Test
    fun testDayTotal() {
        mvc.perform(get("/user/day/total").param("email", "testmockaccount@test.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.calories", `is`(notNullValue())))
    }

    @AfterAll
    fun destroy() {
        mvc.perform(delete("/user/delete/email").param("email", "testmockaccount@test.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
        mvc.perform(delete("/user/delete/email").param("email", "testmockfriend@test.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.response", equalTo(1)))
    }
}

