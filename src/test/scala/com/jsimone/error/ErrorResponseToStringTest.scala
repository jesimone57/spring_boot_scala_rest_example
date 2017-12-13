package com.jsimone.error

import org.junit.{Assert, Test}

class ErrorResponseToStringTest {

  @Test
  def toStringTest1(): Unit = {
    val errorResponse1 = new ErrorResponseBody(400, "/root", "error")
    errorResponse1.method = "GET"

    val expectedResult = """ErrorResponseBody { code: 400, path: /root, message: error, method: GET, errors: [ListBuffer()] }"""
    Assert.assertEquals(expectedResult, errorResponse1.toString)
  }

  @Test
  def toStringTest2(): Unit = {
    val errorResponse1 = new ErrorResponseBody(400, "/root", "error")
    errorResponse1.method = "GET"
    errorResponse1.errors += new FieldError("a", "b", "c")

    val expectedResult = """ErrorResponseBody { code: 400, path: /root, message: error, method: GET, errors: [ListBuffer(FieldError { field: a, value: b, message: c })] }"""
    Assert.assertEquals(expectedResult, errorResponse1.toString)
  }
}

