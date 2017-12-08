package com.jsimone

import com.jsimone.controller.HelloWorldController
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc


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
  def test1(): Unit = {
    mockMvc.perform(get("/")
        .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello World"))
  }

  @Test
  def test2(): Unit = {
    mockMvc.perform(get("/hello1")
      .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
      .andExpect(status.isOk)
      .andExpect(content.contentType("text/plain;charset=UTF-8"))
      .andExpect(content.string("Hello, World"))
  }

  @Test
  def test3(): Unit = {
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

  /**
    * {
        status_code: 400,
        uri_path: "/hello1.1",
        method: "GET",
        error_message: "Required String parameter 'name' is not present",
        errors: [ ]
      }
    */
  @Test
  def test5(): Unit = {
    mockMvc.perform(get("/hello1.1")
      .accept(MediaType.parseMediaType("application/json")))
      .andExpect(status.isBadRequest)
      .andExpect(content.contentType("application/json"))

  }

}
