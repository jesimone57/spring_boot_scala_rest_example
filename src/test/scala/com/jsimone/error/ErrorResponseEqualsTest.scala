package com.jsimone.error

import org.junit.{Assert, Test}

class ErrorResponseEqualsTest {

  @Test
  def isEqual0(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"

    Assert.assertEquals(errorResponse1, errorResponse1)
  }

  @Test
  def isEqual1(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GET"

    Assert.assertEquals(errorResponse1, errorResponse2)
  }

  @Test
  def isEqual2(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    errorResponse1.errors += new FieldError("a", "b", "c")
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GET"
    errorResponse2.errors += new FieldError("a", "b", "c")

    Assert.assertEquals(errorResponse1, errorResponse2)
  }

  @Test
  def isNotEqual1(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(401, "/root", "error")
    errorResponse2.method = "GET"

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def isNotEqual2(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(400, "/roott", "error")
    errorResponse2.method = "GET"

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def isNotEqual3(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(400, "/root", "errorr")
    errorResponse2.method = "GET"

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def isNotEqual4(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GETT"

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def isNotEqual5(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    errorResponse1.errors += new FieldError("a", "b", "c")
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GET"
    errorResponse2.errors += new FieldError("a", "b", "d")

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def isNotEqual6(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    errorResponse1.errors += new FieldError("a", "b", "c")
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GET"
    errorResponse2.errors += new FieldError("a", "b", "c")
    errorResponse2.errors += new FieldError("a", "b", "c")

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }
}
