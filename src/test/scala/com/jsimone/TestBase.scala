package com.jsimone

import com.jsimone.error.{ErrorResponse, FieldError}
import com.jsimone.util.JsonUtil
import org.junit.Assert
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}

class TestBase {
  protected val APPLICATION_JSON = "[application/json]"
  protected val CONTENT_TYPE = "Content-Type"

  protected def verifyErrorResponse(responseEntity: ResponseEntity[String],
                                    expectedHttpStatus: HttpStatus,
                                    expectedMethod: String,
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
                                          expectedMethod: String,
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

  protected def verifyFieldErrors(responseEntity: ResponseEntity[String],
                                  expectedErrors: List[FieldError]): Unit = {

    val json = responseEntity.getBody
    val errorResponse: ErrorResponse = JsonUtil.fromJson[ErrorResponse](responseEntity.getBody)
    Assert.assertEquals(errorResponse.errors.length, expectedErrors.length)

    errorResponse.errors.foreach(fe => Assert.assertTrue(s"$fe not found in $expectedErrors", expectedErrors.contains(fe)))
    //errorResponse.errors.foreach(fe => println(fe +" "+expectedErrors.contains(fe)))
    val headers: HttpHeaders = responseEntity.getHeaders
    Assert.assertEquals(APPLICATION_JSON, headers.get(CONTENT_TYPE).toString)
  }
}

