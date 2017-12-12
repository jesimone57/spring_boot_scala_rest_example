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
      .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello, null"))
  }

  @Test
  def testHello0_2(): Unit = {
    mockMvc.perform(get("/hello0?name=")
      .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello, "))
  }

  @Test
  def testHello0_3(): Unit = {
    mockMvc.perform(get("/hello0?name=Bobby")
      .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello, Bobby"))
  }

  @Test
  def testHelloRoot(): Unit = {
    mockMvc.perform(get("/")
      .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello World"))
  }

  @Test
  def testHello1_1(): Unit = {
    mockMvc.perform(get("/hello1")
      .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello, World"))
  }

  @Test
  def testHello1_2(): Unit = {
    mockMvc.perform(get("/hello1?name=Fred")
      .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello, Fred"))
  }

  @Test
  def test4(): Unit = {
    mockMvc.perform(get("/hello1.1?name=Fred")
      .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello, Fred"))
  }

}
