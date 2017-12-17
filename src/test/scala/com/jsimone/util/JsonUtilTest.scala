package com.jsimone.util

import com.jsimone.entity.Person
import com.jsimone.error.{ErrorResponse, FieldError}
import org.junit.{Assert, Test}
import org.springframework.http.HttpMethod

class JsonUtilTest {

  @Test
  def personToJson()  {
    val json = """{"name":"frank","age":20,"job":"artist"}"""

    val person: Person = new Person("frank", 20, "artist")

    val actual = JsonUtil.toJson(person)
    Assert.assertEquals(json, actual)
  }

  @Test
  def personFromJson()  {
    val json = """{"name":"frank","age":20,"job":"artist"}"""

    val person: Person = new Person("frank", 20, "artist")

    val personActual: Person = JsonUtil.fromJson[Person](json)
    Assert.assertEquals(person, personActual)
  }

  @Test
  def errorResponseFromJson(): Unit = {
    val json = """{"status_code":400,"uri_path":"/hello1.2","method":"GET","error_message":"Required int parameter 'num' is not present","errors":[]}"""

    val expectedErrorResponse: ErrorResponse = new ErrorResponse(400, "/hello1.2", HttpMethod.GET, "Required int parameter 'num' is not present")

    val actualErrorResponse: ErrorResponse = JsonUtil.fromJson[ErrorResponse](json)
    Assert.assertEquals(expectedErrorResponse, actualErrorResponse)
  }

  @Test
  def fieldErrorFromJson(): Unit = {
    val json = """{"field_name":"name","rejected_value":"ff","error_message":"name is too short"}"""

    val fieldError: FieldError = new FieldError("name", "ff", "name is too short")

    val actualFieldError: FieldError = JsonUtil.fromJson[FieldError](json)
    Assert.assertEquals(fieldError, actualFieldError)
  }
}
