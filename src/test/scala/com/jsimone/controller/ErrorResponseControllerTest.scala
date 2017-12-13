package com.jsimone.controller

import com.jsimone.TestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ErrorResponseControllerTest extends TestBase {

  @LocalServerPort
  private val port: Int = 0

  @Autowired
  private val restTemplate: TestRestTemplate  = null

  /**
    {
      "status_code": 500,
      "uri_path": "/thrown_exception1",
      "error_message": "com.jsimone.controller.ErrorResponseController.throwingAnException1 ....",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def thrownException1Response(): Unit = {
    val url = "http://localhost:" + port +"/thrown_exception1"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponsePrefix(responseEntity, HttpStatus.INTERNAL_SERVER_ERROR, "GET",
      "com.jsimone.controller.ErrorResponseController.throwingAnException1")
  }

  /**
    {
      "status_code": 500,
      "uri_path": "/thrown_exception2",
      "error_message": "com.jsimone.controller.ErrorResponseController.throwingAnException2 .....",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def thrownException2Response(): Unit = {
    val url = "http://localhost:" + port +"/thrown_exception2"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponsePrefix(responseEntity, HttpStatus.INTERNAL_SERVER_ERROR, "GET",
      "com.jsimone.controller.ErrorResponseController.throwingAnException2")
  }
}