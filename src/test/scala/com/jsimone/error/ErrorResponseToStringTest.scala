package com.jsimone.error

import org.junit.{Assert, Test}
import org.springframework.http.HttpMethod

class ErrorResponseToStringTest {

  @Test
  def testToString1(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")

    val expectedResult = """ErrorResponse { code: 400, path: /root, message: error, method: GET, errors: [ListBuffer()] }"""
    Assert.assertEquals(expectedResult, errorResponse1.toString)
  }

  @Test
  def testToString2(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
    errorResponse1.errors += new FieldError("a", "b", "c")

    val expectedResult = """ErrorResponse { code: 400, path: /root, message: error, method: GET, errors: [ListBuffer(FieldError { field: a, value: b, message: c })] }"""
    Assert.assertEquals(expectedResult, errorResponse1.toString)
  }
}

