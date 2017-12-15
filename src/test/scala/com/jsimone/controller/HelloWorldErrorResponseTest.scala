package com.jsimone.controller

import com.jsimone.TestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HelloWorldErrorResponseTest extends TestBase {

  @Test
  @throws[Exception]
  def helloWorld(): Unit = {
    assertThat(restTemplate.getForObject("http://localhost:" + port + "/", classOf[String])).contains("Hello World")
  }

  /**
      {
        status_code: 400,
        uri_path: "/hello1.1",
        method: "GET",
        error_message: "Required String parameter 'name' is not present",
        errors: [ ]
      }
    */
  @Test
  def hello1_1(): Unit = {
    val url = "http://localhost:" + port +"/hello1.1"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "Required String parameter 'name' is not present")
  }

  /**
      {
        "status_code": 400,
        "uri_path": "/hello1.2",
        "error_message": "Required int parameter 'num' is not present",
        "errors": [],
        "method": "GET"
      }
    */
  @Test
  def hello1_2(): Unit = {
    val url = "http://localhost:" + port + "/hello1.2"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "Required int parameter 'num' is not present")
  }

  /**
      {
        "status_code": 400,
        "uri_path": "/hello1.2",
        "error_message": "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: """,
        "method": "GET",
        "errors": [],
      }
    */
  @Test
  def hello1_2_2(): Unit = {
    val url = "http://localhost:" + port + "hello1.2?num="
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET",
      "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"\"")
  }

  /**
      {
        "status_code": 400,
        "uri_path": "/hello1.2",
        "error_message": "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: "asdf"",
        "method": "GET",
        "errors": [],
      }
    */
  @Test
  def hello1_2_3(): Unit = {
    val url = "http://localhost:" + port + "hello1.2?num=asdf"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET",
      "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"asdf\"")
  }

  /**
    {
      "status_code": 404,
      "uri_path": "/hello2",
      "error_message": "The URL you have reached is not in service at this time",
      "method": null,
      "errors": [],
    }
    */
  @Test
  def hello2(): Unit = {
    val url = "http://localhost:" + port + "/hello2"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.NOT_FOUND, null, "The URL you have reached is not in service at this time")
  }

  /**
    {
      "status_code": 400,
      "uri_path": "/hello2.1/asdf",
      "error_message": "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: "asdf"",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def hello2_1(): Unit = {
    val url = "http://localhost:" + port + "/hello2.1/asdf"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET",
      "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"asdf\"")
  }

  /**
  {
    "status_code": 400,
    "uri_path": "/hello4",
    "error_message": "org.springframework.validation.BeanPropertyBindingResult: 2 errors Field error in object 'person' on field 'job': rejected value [null]; codes [NotBlank.person.job,NotBlank.job,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [person.job,job]; arguments []; default message [job]]; default message [may not be empty] Field error in object 'person' on field 'age': rejected value [0]; codes [Min.person.age,Min.age,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [person.age,age]; arguments []; default message [age],18]; default message [Age should be a minmium of 18]",
    "method": "GET",
    "errors": [
      {
      "field_name": "job",
      "error_message": "may not be empty",
      "rejected_value": null
      },
      {
      "field_name": "age",
      "error_message": "Age should be a minium of 18",
      "rejected_value": "0"
      }
    ],
  }
    */
  @Test
  def hello4(): Unit = {
    val url = "http://localhost:" + port + "/hello4"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponsePrefix(responseEntity, HttpStatus.BAD_REQUEST, "GET", "org.springframework.validation.BeanPropertyBindingResult: 2 errors\nField error in object 'person'")
  }
}