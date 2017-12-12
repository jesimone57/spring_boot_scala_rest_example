package com.jsimone.util

import com.jsimone.entity.Person
import com.jsimone.error.{ErrorResponseBody, FieldError}
import org.junit.{Assert, Test}

class JsonUtilTest {

  @Test
  def toJsonTest()  {
    val json: String = "{\"name\":\"frank\",\"age\":20,\"job\":\"artist\"}"

    val person: Person = new Person
    person.name = "frank"
    person.age = 20
    person.job = "artist"

    val actual = JsonUtil.toJson(person)
    Assert.assertEquals(json, actual)
  }

  @Test
  def fromJsonTest()  {
    val json: String = "{\"name\":\"frank\",\"age\":20,\"job\":\"artist\"}"

    val person: Person = new Person
    person.name = "frank"
    person.age = 20
    person.job = "artist"

    val personActual: Person = JsonUtil.fromJson[Person](json)
    Assert.assertEquals(person, personActual)
  }

  @Test
  def fromJsonErrorResponse(): Unit = {
    val json = "{\"status_code\":400,\"uri_path\":\"/hello1.2\",\"method\":\"GET\",\"error_message\":\"Required int parameter 'num' is not present\",\"errors\":[]}"
    println(json)
    val actualErrorResponse: ErrorResponseBody = JsonUtil.fromJson[ErrorResponseBody](json)
    println(actualErrorResponse)
    //Assert.assertEquals(expectedJson, actualErrorResponse)
  }

  @Test
  def fromJsonFieldError(): Unit = {
    val json = "{\"field_name\":\"name\",\"rejected_value\":\"ff\",\"error_message\":\"name is too short\"}"

    val fieldError: FieldError = new FieldError
    fieldError.field = "name"
    fieldError.value = "ff"
    fieldError.message = "name is too short"

    val actualFieldError: FieldError = JsonUtil.fromJson[FieldError](json)
    Assert.assertEquals(fieldError, actualFieldError)
  }
}
