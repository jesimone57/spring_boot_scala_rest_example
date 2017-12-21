package com.jsimone

import com.jsimone.error.{ErrorResponse, FieldError}
import com.jsimone.util.JsonUtil
import org.assertj.core.api.Assertions
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http._

abstract class AbstractTestBase {
  protected val APPLICATION_JSON = "[application/json]"
  protected val TEXT_PLAIN = "[text/plain;charset=UTF-8]"
  protected val CONTENT_TYPE = "Content-Type"


  @LocalServerPort
  protected val port: Int = 0

  @Autowired
  protected val restTemplate: TestRestTemplate = null


  protected def verifySuccessResponse(responseEntity: ResponseEntity[String], expectedStatus: HttpStatus, expectedMediaType: MediaType, expectedResponseBody: String): Unit = {
    Assertions.assertThat(responseEntity.getBody).isEqualTo(expectedResponseBody)
    Assertions.assertThat(responseEntity.getStatusCodeValue).isEqualTo(expectedStatus.value())
    Assertions.assertThat(responseEntity.getHeaders.getContentType.toString).isEqualTo(expectedMediaType.toString)
  }

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
    Assert.assertTrue(s"message\n${errorResponse.message}\ndoes not start with\n$messagePrefix", errorResponse.message.startsWith(messagePrefix))

    Assert.assertEquals(expectedHttpStatus, responseEntity.getStatusCode)

    val headers: HttpHeaders = responseEntity.getHeaders
    Assert.assertEquals(APPLICATION_JSON, headers.get(CONTENT_TYPE).toString)
  }

  protected def verifyFieldErrors(totalFieldErrorsExpected: Int, errorResponse: ErrorResponse, expectedFieldErrors: List[FieldError]): Unit = {
    Assert.assertEquals(totalFieldErrorsExpected, errorResponse.errors.length)
    expectedFieldErrors.foreach { efe =>
      println("--> found = " + errorResponse.errors.contains(efe) + "   " + efe)
      Assert.assertTrue(s"$efe not found", errorResponse.errors.contains(efe))
    }
  }
}

