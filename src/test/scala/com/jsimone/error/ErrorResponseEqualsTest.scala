package com.jsimone.error

import org.junit.{Assert, Test}
import org.springframework.http.HttpMethod

class ErrorResponseEqualsTest {

    @Test
    def equalsTest1(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")

        Assert.assertEquals(errorResponse1, errorResponse1)
    }

    @Test
    def equalsTest2(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        val errorResponse2 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")

        Assert.assertEquals(errorResponse1, errorResponse2)
    }

    @Test
    def equalsTest3(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        errorResponse1.errors += new FieldError("a", "b", "c")
        val errorResponse2 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        errorResponse2.errors += new FieldError("a", "b", "c")

        Assert.assertEquals(errorResponse1, errorResponse2)
    }

    @Test
    def notEqualsTest1(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        val errorResponse2 = new ErrorResponse(401, "/root", HttpMethod.GET, "error")

        Assert.assertNotEquals(errorResponse1, errorResponse2)
    }

    @Test
    def notEqualsTest2(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        val errorResponse2 = new ErrorResponse(400, "/roott", HttpMethod.GET, "error")

        Assert.assertNotEquals(errorResponse1, errorResponse2)
    }

    @Test
    def notEqualsTest3(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        val errorResponse2 = new ErrorResponse(400, "/root", HttpMethod.GET, "errorr")


        Assert.assertNotEquals(errorResponse1, errorResponse2)
    }

    @Test
    def notEqualsTest4(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        val errorResponse2 = new ErrorResponse(400, "/root", HttpMethod.PUT, "error")

        Assert.assertNotEquals(errorResponse1, errorResponse2)
    }

    @Test
    def notEqualsTest5(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        errorResponse1.errors += new FieldError("a", "b", "c")
        val errorResponse2 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        errorResponse2.errors += new FieldError("a", "b", "d")

        Assert.assertNotEquals(errorResponse1, errorResponse2)
    }

    @Test
    def notEqualsTest6(): Unit = {
        val errorResponse1 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        errorResponse1.errors += new FieldError("a", "b", "c")
        val errorResponse2 = new ErrorResponse(400, "/root", HttpMethod.GET, "error")
        errorResponse2.errors += new FieldError("a", "b", "c")
        errorResponse2.errors += new FieldError("a", "b", "c")

        Assert.assertNotEquals(errorResponse1, errorResponse2)
    }
}
