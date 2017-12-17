package com.jsimone.controller

import com.jsimone.TestBase
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.{HttpStatus, MediaType}
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JsonResponseControllerTest extends TestBase {

  @Test
  def jsonResponseTest(): Unit = {
    val url = "http://localhost:" + port + "/json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    val expectedResponse =
      """{"name":"john doe","age":18,"hasChild":true,"childs":[{"name":"dorothy","age":5,"hasChild":false},{"name":"bill","age":8,"hasChild":false}]}"""

    verifySuccessResponse(responseEntity, HttpStatus.OK, MediaType.APPLICATION_JSON_UTF8, expectedResponse)
  }
}

