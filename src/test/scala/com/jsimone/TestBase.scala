package com.jsimone

import com.jsimone.error.ErrorResponse
import com.jsimone.util.JsonUtil
import org.junit.Assert
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}

class TestBase {
  protected val APPLICATION_JSON = "[application/json]"
  protected val CONTENT_TYPE = "Content-Type"

  protected def verifyBadRequestErrorResponse(responseEntity: ResponseEntity[String],
                                            expectedStatusCode: Int,
                                            expectedMethod: String,
                                            expectedMessage: String): Unit = {

    val json = responseEntity.getBody
    println(json)
    val errorResponse: ErrorResponse = JsonUtil.fromJson[ErrorResponse](responseEntity.getBody)

    Assert.assertEquals(expectedStatusCode, errorResponse.code)
    Assert.assertEquals(expectedMethod, errorResponse.method)
    Assert.assertEquals(expectedMessage, errorResponse.message)

    Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode)

    val headers: HttpHeaders = responseEntity.getHeaders
    Assert.assertEquals(APPLICATION_JSON, headers.get(CONTENT_TYPE).toString)
  }
}

