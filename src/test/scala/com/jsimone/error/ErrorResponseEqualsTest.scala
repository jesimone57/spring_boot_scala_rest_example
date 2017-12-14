package com.jsimone.error

import org.junit.{Assert, Test}

class ErrorResponseEqualsTest {

  @Test
  def equalsTest1(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"

    Assert.assertEquals(errorResponse1, errorResponse1)
  }

  @Test
  def equalsTest2(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GET"

    Assert.assertEquals(errorResponse1, errorResponse2)
  }

  @Test
  def equalsTest3(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    errorResponse1.errors += new FieldError("a", "b", "c")
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GET"
    errorResponse2.errors += new FieldError("a", "b", "c")

    Assert.assertEquals(errorResponse1, errorResponse2)
  }

  @Test
  def notEqualsTest1(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(401, "/root", "error")
    errorResponse2.method = "GET"

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def notEqualsTest2(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(400, "/roott", "error")
    errorResponse2.method = "GET"

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def notEqualsTest3(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(400, "/root", "errorr")
    errorResponse2.method = "GET"

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def notEqualsTest4(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GETT"

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def notEqualsTest5(): Unit = {
    val errorResponse1 = new ErrorResponse(400, "/root", "error")
    errorResponse1.method = "GET"
    errorResponse1.errors += new FieldError("a", "b", "c")
    val errorResponse2 = new ErrorResponse(400, "/root", "error")
    errorResponse2.method = "GET"
    errorResponse2.errors += new FieldError("a", "b", "d")

    Assert.assertNotEquals(errorResponse1, errorResponse2)
  }

  @Test
  def notEqualsTest6(): Unit = {
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
