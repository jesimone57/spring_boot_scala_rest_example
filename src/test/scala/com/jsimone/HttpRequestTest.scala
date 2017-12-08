package com.jsimone

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

  @LocalServerPort
  private val port: Int = 0

  @Autowired
  private val restTemplate: TestRestTemplate  = null

  @Test
  @throws[Exception]
  def greetingShouldReturnDefaultMessage(): Unit = {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", classOf[String])).contains("Hello World")
  }

  @Test
  def hello1_1(): Unit = {
    val url = "http://localhost:" + port +"/hello1.1"
    val response = restTemplate.getForObject(url, classOf[String])
    println(response)
    //assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", classOf[String])).contains("Hello World")
  }
}