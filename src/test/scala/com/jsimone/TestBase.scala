package com.jsimone

import com.jsimone.error.{ErrorResponse, FieldError}
import com.jsimone.util.JsonUtil
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.{HttpHeaders, HttpMethod, HttpStatus, ResponseEntity}

class TestBase {
  protected val APPLICATION_JSON = "[application/json]"
  protected val TEXT_PLAIN = "[text/plain;charset=UTF-8]"
  protected val CONTENT_TYPE = "Content-Type"


  @LocalServerPort
  protected val port: Int = 0

  @Autowired
  protected val restTemplate: TestRestTemplate  = null


  protected def verifyErrorResponse(responseEntity: ResponseEntity[String],
                                    expectedHttpStatus: HttpStatus,
                                    expectedMethod: HttpMethod,
                                    expectedMessage: String): ErrorResponse = {

    val json = responseEntity.getBody
    println(json)
    val errorResponse: ErrorResponse = JsonUtil.fromJson[ErrorResponse](responseEntity.getBody)

    Assert.assertEquals(expectedHttpStatus.value(), errorResponse.code)
    Assert.assertEquals(expectedMethod, errorResponse.method)
    Assert.assertEquals(expectedMessage, errorResponse.message)

    Assert.assertEquals(expectedHttpStatus, responseEntity.getStatusCode)

    val headers: HttpHeaders = responseEntity.getHeaders
    Assert.assertEquals(APPLICATION_JSON, headers.get(CONTENT_TYPE).toString)
    errorResponse
  }

  protected def verifyErrorResponsePrefix(responseEntity: ResponseEntity[String],
                                          expectedHttpStatus: HttpStatus,
                                          expectedMethod: HttpMethod,
                                          messagePrefix: String): Unit = {

    val json = responseEntity.getBody
    println(json)
    val errorResponse: ErrorResponse = JsonUtil.fromJson[ErrorResponse](responseEntity.getBody)

    Assert.assertEquals(expectedHttpStatus.value(), errorResponse.code)
    Assert.assertEquals(expectedMethod, errorResponse.method)
    Assert.assertTrue(errorResponse.message.startsWith(messagePrefix))

    Assert.assertEquals(expectedHttpStatus, responseEntity.getStatusCode)

    val headers: HttpHeaders = responseEntity.getHeaders
    Assert.assertEquals(APPLICATION_JSON, headers.get(CONTENT_TYPE).toString)
  }

  protected def verifyFieldErrors(totalFieldErrorsExpected: Int, errorResponse: ErrorResponse, expectedFieldErrors: List[FieldError]): Unit = {
    Assert.assertEquals(totalFieldErrorsExpected, errorResponse.errors.length)
    expectedFieldErrors.foreach{efe =>
      println("--> found = "+ errorResponse.errors.contains(efe) + "   "+ efe)
      Assert.assertTrue(s"$efe not found", errorResponse.errors.contains(efe))}
  }
}

