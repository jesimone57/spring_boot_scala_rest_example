package com.jsimone

import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import org.junit.{Assert, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.{HttpHeaders, HttpStatus}
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HelloWorldErrorResponseTest {

  @LocalServerPort
  private val port: Int = 0

  @Autowired
  private val restTemplate: TestRestTemplate  = null

  private def verifyErrorResponseJson(url: String, expectedResponse: String,
                                      expectedStatus:  HttpStatus, expectedContentType: String): Unit = {
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    val json = responseEntity.getBody
    //println(json)
    Assert.assertEquals(expectedResponse, json)
    val actualStatus = responseEntity.getStatusCode
    //println(actualStatus)
    Assert.assertEquals(expectedStatus, actualStatus)
    val headers: HttpHeaders = responseEntity.getHeaders
    //println(headers)
    val actualContentType = headers.get("Content-Type").toString
    Assert.assertEquals(expectedContentType, actualContentType)
  }

  @Test
  @throws[Exception]
  def greetingShouldReturnDefaultMessage(): Unit = {
    assertThat(restTemplate.getForObject("http://localhost:" + port + "/", classOf[String])).contains("Hello World")
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
  def hello1_1(): Unit = {
    val url = "http://localhost:" + port +"/hello1.1"
    val expectedResponse = "{\"status_code\":400,\"uri_path\":\"/hello1.1\",\"method\":\"GET\",\"error_message\":\"Required String parameter 'name' is not present\",\"errors\":[]}"
    val expectedStatus = HttpStatus.BAD_REQUEST
    val expectedContentType = "[application/json]"
    verifyErrorResponseJson(url, expectedResponse, expectedStatus, expectedContentType)
  }

  /**
    * {
        "status_code": 400,
        "uri_path": "/hello1.2",
        "error_message": "Required int parameter 'num' is not present",
        "errors": [],
        "method": "GET"
      }
    */
  @Test
  def hello1_2(): Unit = {
    val url = "http://localhost:" + port +"/hello1.2"
    val expectedResponse = "{\"status_code\":400,\"uri_path\":\"/hello1.2\",\"method\":\"GET\",\"error_message\":\"Required int parameter 'num' is not present\",\"errors\":[]}"
    val expectedStatus = HttpStatus.BAD_REQUEST
    val expectedContentType = "[application/json]"
    verifyErrorResponseJson(url, expectedResponse, expectedStatus, expectedContentType)
  }
}