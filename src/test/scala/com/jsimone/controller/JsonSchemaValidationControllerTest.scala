package com.jsimone.controller

import com.jsimone.TestBase
import com.jsimone.error.FieldError
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JsonSchemaValidationControllerTest extends TestBase {

  @LocalServerPort
  private val port: Int = 0

  @Autowired
  private val restTemplate: TestRestTemplate = null

  /**
    {
      "status_code": 400,
      "uri_path": "/schema",
      "error_message": "Required String parameter 'input' is not present",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def schemaInputParamMissing1(): Unit = {
    val url = "http://localhost:" + port + "/schema"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "Required String parameter 'input' is not present")
  }

  /**
  {
      "status_code": 400,
      "uri_path": "/schema",
      "error_message": "Required String parameter 'input' is not present",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def schemaInputParamMissing2(): Unit = {
    val url = "http://localhost:" + port + "/schema?schema=/person1.json "
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "Required String parameter 'input' is not present")
  }

  /**
  {
      "status_code": 400,
      "uri_path": "/schema",
      "error_message": "Required String parameter 'schema' is not present",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def schemaSchemaParamMissing(): Unit = {
    val url = "http://localhost:" + port + "/schema?input=/person1.json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "Required String parameter 'schema' is not present")
  }

  /**
  {
      "status_code": 400,
      "uri_path": "/schema",
      "error_message": "resource path does not start with a '/'",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def schemaInputResourceDoesNotBeginWithSlash1(): Unit = {
    val url = "http://localhost:" + port + "/schema?input=person1.json&schema=person1_schema.json "
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "resource path does not start with a '/'")
  }

  /**
  {
      "status_code": 400,
      "uri_path": "/schema",
      "error_message": "resource path does not start with a '/'",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def schemaInputResourceDoesNotBeginWithSlash2(): Unit = {
    val url = "http://localhost:" + port + "/schema?input=/person1.json&schema=person1_schema.json "
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "resource path does not start with a '/'")
  }

  /**
  {
      "status_code": 400,
      "uri_path": "/schema",
      "error_message": "resource path does not start with a '/'",
      "method": "GET",
      "errors": [],
    }
    */
  @Test
  def schemaInputResourceDoesNotBeginWithSlash3(): Unit = {
    val url = "http://localhost:" + port + "/schema?input=person1.json&schema=/person1_schema.json "
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "resource path does not start with a '/'")
  }

  /**
  {
    "status_code": 400,
    "uri_path": "/schema",
    "error_message": "JSON Schema validation errors encountered.",
    "method": "GET",
    "errors": [
        {
        "field_name": "/age",
        "error_message": "numeric instance is lower than the required minimum (minimum: 18, found: 17)",
        "rejected_value": "17"
        },
        {
        "field_name": "/job",
        "error_message": "ECMA 262 regex "^[A-Za-z0-9]+$" does not match input string "pi*lot"",
        "rejected_value": "pi*lot"
        },
        {
        "field_name": "/name",
        "error_message": "string "f" is too short (length: 1, required minimum: 2)",
        "rejected_value": "f"
        }
      ],
    }
    */
  @Test
  def schemaValidationFailure(): Unit = {
    val url = "http://localhost:" + port + "/schema?input=/person1.json&schema=/person1_schema.json "
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "JSON Schema validation errors encountered.")

    val expectedFieldErrors = List(
      new FieldError("/age", "17", "numeric instance is lower than the required minimum (minimum: 18, found: 17)"),
      new FieldError("/job", "pi*lot", """ECMA 262 regex "^[A-Za-z0-9]+$" does not match input string "pi*lot""""),
      new FieldError("/name",  "f", "string \"f\" is too short (length: 1, required minimum: 2)")
    )
    verifyFieldErrors(responseEntity, expectedFieldErrors)
  }
}


