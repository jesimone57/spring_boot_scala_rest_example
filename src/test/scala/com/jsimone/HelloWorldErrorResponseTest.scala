package com.jsimone

import com.jsimone.error.ErrorResponseBody
import com.jsimone.util.JsonUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import org.junit.{Assert, Ignore, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HelloWorldErrorResponseTest extends TestBase {

  @LocalServerPort
  private val port: Int = 0

  @Autowired
  private val restTemplate: TestRestTemplate  = null

  @Test
  @throws[Exception]
  def helloWorld(): Unit = {
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
  @Ignore
  def hello1_1(): Unit = {
    val url = "http://localhost:" + port +"/hello1.1"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyBadRequestErrorResponse(responseEntity, 400, "GET", "Required String parameter 'name' is not present")
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
  @Ignore
  def hello1_2(): Unit = {
    val url = "http://localhost:" + port + "/hello1.2"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyBadRequestErrorResponse(responseEntity, 400, "GET", "Required int parameter 'num' is not present")
  }
}