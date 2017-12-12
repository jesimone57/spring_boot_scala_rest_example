package com.jsimone.util

import com.jsimone.entity.Person
import com.jsimone.error.{ErrorResponseBody, FieldError}
import org.junit.{Assert, Test}

class JsonUtilTest {

  @Test
  def toJsonPerson()  {
    val json = """{"name":"frank","age":20,"job":"artist"}"""

    val person: Person = new Person("frank", 20, "artist")

    val actual = JsonUtil.toJson(person)
    Assert.assertEquals(json, actual)
  }

  @Test
  def fromJsonPerson()  {
    val json = """{"name":"frank","age":20,"job":"artist"}"""

    val person: Person = new Person("frank", 20, "artist")

    val personActual: Person = JsonUtil.fromJson[Person](json)
    Assert.assertEquals(person, personActual)
  }

  @Test
  def fromJsonErrorResponse(): Unit = {
    val json = """{"status_code":400,"uri_path":"/hello1.2","method":"GET","error_message":"Required int parameter 'num' is not present","errors":[]}"""

    val expectedErrorResponse: ErrorResponseBody = new ErrorResponseBody(400, "/hello1.2", "Required int parameter 'num' is not present")
    expectedErrorResponse.method = "GET"

    val actualErrorResponse: ErrorResponseBody = JsonUtil.fromJson[ErrorResponseBody](json)
    Assert.assertEquals(expectedErrorResponse, actualErrorResponse)
  }

  @Test
  def fromJsonFieldError(): Unit = {
    val json = """{"field_name":"name","rejected_value":"ff","error_message":"name is too short"}"""

    val fieldError: FieldError = new FieldError("name", "ff", "name is too short")

    val actualFieldError: FieldError = JsonUtil.fromJson[FieldError](json)
    Assert.assertEquals(fieldError, actualFieldError)
  }
}
