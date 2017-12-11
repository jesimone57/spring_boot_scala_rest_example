package com.jsimone.util

import com.jsimone.entity.Person
import com.jsimone.error.ErrorResponseBody
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

  def fromJsonErrorResponse(): Unit = {
    val json = "{\"status_code\":400,\"uri_path\":\"/hello1.2\",\"method\":\"GET\",\"error_message\":\"Required int parameter 'num' is not present\",\"errors\":[]}"
    val errorResponseActual: ErrorResponseBody = JsonUtil.fromJson[ErrorResponseBody](json)
  }
}
