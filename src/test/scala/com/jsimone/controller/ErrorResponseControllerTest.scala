package com.jsimone.controller

import com.jsimone.TestBase
import com.jsimone.error.FieldError
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.{HttpMethod, HttpStatus}
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ErrorResponseControllerTest extends TestBase {

  /**
        {
        "status_code": 400,
        "uri_path": "/error_response",
        "error_message": "example request validation errors",
        "method": "GET",
        "errors": [
            {
            "field_name": "name",
            "error_message": "required name is empty or null",
            "rejected_value": ""
            },
            {
            "field_name": "age",
            "error_message": "invalid age given",
            "rejected_value": "0"
            },
            {
            "field_name": "phone",
            "error_message": "invalid phone number provided",
            "rejected_value": "123"
            }
          ],
        }
    */
  @Test
  def errorResponseTest(): Unit = {
    val url = "http://localhost:" + port + "/error_response"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    val errorResponse = verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "example request validation errors")

    val expectedFieldErrors = List(
      new FieldError("name", "", "required name is empty or null"),
      new FieldError("age", "0", "invalid age given"),
      new FieldError("phone",  "123", "invalid phone number provided")
    )
    verifyFieldErrors(3, errorResponse, expectedFieldErrors)
  }

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
    verifyErrorResponsePrefix(responseEntity, HttpStatus.INTERNAL_SERVER_ERROR, HttpMethod.GET,
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
    verifyErrorResponsePrefix(responseEntity, HttpStatus.INTERNAL_SERVER_ERROR, HttpMethod.GET,
      "com.jsimone.controller.ErrorResponseController.throwingAnException2")
  }
}