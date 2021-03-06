package com.jsimone.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.{content, status}


@RunWith(classOf[SpringRunner])
@WebMvcTest(value = Array(classOf[HelloWorldController]), secure = false)
class HelloWorldControllerTest {

    private val contentType = "text/plain;charset=UTF-8"

    @Autowired
    private val controller: HelloWorldController = null

    @Autowired
    private val mockMvc: MockMvc = null

    @Test
    @throws[Exception]
    def contextLoads(): Unit = {
        println(controller)
        assertThat(controller).isNotNull
    }


    @Test
    def testHello0_1(): Unit = {
        mockMvc.perform(get("/hello0")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, null"))
    }

    @Test
    def testHello0_2(): Unit = {
        mockMvc.perform(get("/hello0?name=")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, "))
    }

    @Test
    def testHello0_3(): Unit = {
        mockMvc.perform(get("/hello0?name=Bobby")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, Bobby"))
    }

    @Test
    def testHelloRoot(): Unit = {
        mockMvc.perform(get("/")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello World"))
    }

    @Test
    def testHello1_1(): Unit = {
        mockMvc.perform(get("/hello1")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, World"))
    }

    @Test
    def testHello1_2(): Unit = {
        mockMvc.perform(get("/hello1?name=Fred")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, Fred"))
    }

    @Test
    def test4(): Unit = {
        mockMvc.perform(get("/hello1.1?name=Fred")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, Fred"))
    }

    @Test
    def test8(): Unit = {
        mockMvc.perform(get("/hello1.2?num=11")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, 11"))
    }

    @Test
    def test5(): Unit = {
        mockMvc.perform(get("/hello2/Fred")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, Fred"))
    }

    @Test
    def test9(): Unit = {
        mockMvc.perform(get("/hello2.1/11")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello, 11"))
    }

    /**
      * No validation, therefore missing request parameters do not cause an error response
      */
    @Test
    def test6(): Unit = {
        mockMvc.perform(get("/hello3")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello none, whose age is 0 and job is null"))
    }

    /**
      * No validation, therefore request parameters are not bound by constraints and do not cause an error response
      */
    @Test
    def test7(): Unit = {
        mockMvc.perform(get("/hello3?name=f&age=1&job=****")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello f, whose age is 1 and job is ****"))
    }

    @Test
    def test10(): Unit = {
        mockMvc.perform(get("/hello4?name=Frank&age=35&job=Fisherman")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello Frank, whose age is 35 and job is Fisherman"))
    }

    @Test
    def test11(): Unit = {
        mockMvc.perform(get("/hello5?name=")
            .accept(MediaType.parseMediaType(contentType)))
            .andExpect(status.isOk)
            .andExpect(content.contentType(contentType))
            .andExpect(content.string("Hello "))
    }
}
