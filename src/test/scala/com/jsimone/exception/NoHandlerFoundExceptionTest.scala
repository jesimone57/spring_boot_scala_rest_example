package com.jsimone.exception

import com.jsimone.AbstractTestBase
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@ActiveProfiles(Array("test"))
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NoHandlerFoundExceptionTest extends AbstractTestBase {

  /**
    {
      "status_code": 404,
      "uri_path": "/asdf",
      "error_message": "The URL you have reached is not in service at this time",
      "method": null,
      "errors": [],
    }
    */
  @Test
  def invalidEndpointTest(): Unit = {
    val url = "http://localhost:" + port + "/asdfasdf"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.NOT_FOUND, null, "The URL you have reached is not in service at this time")
  }
}

