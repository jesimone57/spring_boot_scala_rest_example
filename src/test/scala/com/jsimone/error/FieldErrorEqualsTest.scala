package com.jsimone.error

import org.junit.{Assert, Test}

class FieldErrorEqualsTest {

    @Test
    def equalsTest1(): Unit = {
        val fieldError1 = new FieldError("field", "value", "message")

        Assert.assertEquals(fieldError1, fieldError1)
    }

    @Test
    def equalsTest2(): Unit = {
        val fieldError1 = new FieldError("field", "value", "message")
        val fieldError2 = new FieldError("field", "value", "message")

        Assert.assertEquals(fieldError1, fieldError2)
    }

    @Test
    def equalsTest3(): Unit = {
        val fieldError1 = new FieldError()
        val fieldError2 = new FieldError()

        Assert.assertEquals(fieldError1, fieldError2)
    }

    @Test
    def notEqualsTest1(): Unit = {
        val fieldError1 = new FieldError("field", "value", "message")
        val fieldError2 = new FieldError("field1", "value", "message")

        Assert.assertNotEquals(fieldError1, fieldError2)
    }

    @Test
    def notEqualsTest2(): Unit = {
        val fieldError1 = new FieldError("field", "value", "message")
        val fieldError2 = new FieldError("field", "value1", "message")

        Assert.assertNotEquals(fieldError1, fieldError2)
    }

    @Test
    def notEqualsTest3(): Unit = {
        val fieldError1 = new FieldError("field", "value", "message")
        val fieldError2 = new FieldError("field", "value", "message1")

        Assert.assertNotEquals(fieldError1, fieldError2)
    }

}
