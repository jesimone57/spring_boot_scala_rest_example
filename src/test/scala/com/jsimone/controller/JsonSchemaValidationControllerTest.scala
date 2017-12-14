package com.jsimone.controller

import com.jsimone.TestBase
import com.jsimone.error.FieldError
import org.junit.runner.RunWith
import org.junit.{Assert, Test}
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.{HttpHeaders, HttpStatus}
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JsonSchemaValidationControllerTest extends TestBase {

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
    val errorResponse = verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "JSON Schema validation errors encountered.")

    val expectedFieldErrors = List(
      new FieldError("/age", "17", "numeric instance is lower than the required minimum (minimum: 18, found: 17)"),
      new FieldError("/job", "pi*lot", """ECMA 262 regex "^[A-Za-z0-9]+$" does not match input string "pi*lot""""),
      new FieldError("/name",  "f", "string \"f\" is too short (length: 1, required minimum: 2)")
    )
    verifyFieldErrors(expectedFieldErrors.length, errorResponse, expectedFieldErrors)
  }

  /**
      {
        "status_code": 400,
        "uri_path": "/schema_test1",
        "error_message": "JSON Schema validation errors encountered.",
        "method": "GET",
        "errors": [{
          "field_name": "/bool",
          "error_message": "instance type (string) does not match any allowed primitive type (allowed: [\"boolean\"])",
          "rejected_value": "string"
        }, {
          "field_name": "/date",
          "error_message": "string \"2017\" is invalid against requested date format(s) [yyyy-MM-dd'T'HH:mm:ssZ, yyyy-MM-dd'T'HH:mm:ss.[0-9]{1,12}Z]",
          "rejected_value": "2017"
        }, {
          "field_name": "/email",
          "error_message": "string \"asdf\" is not a valid email address",
          "rejected_value": "asdf"
        }, {
          "field_name": "/enum",
          "error_message": "instance value (\"AZURE\") not found in enum (possible values: [\"RED\",\"BLACK\",\"BLUE\"])",
          "rejected_value": "AZURE"
        }, {
          "field_name": "/int-max",
          "error_message": "numeric instance is greater than the required maximum (maximum: 100, found: 1000)",
          "rejected_value": "1000"
        }, {
          "field_name": "/int-min",
          "error_message": "numeric instance is lower than the required minimum (minimum: 50, found: 17)",
          "rejected_value": "17"
        }, {
          "field_name": "/int-wrongtype",
          "error_message": "instance type (number) does not match any allowed primitive type (allowed: [\"integer\"])",
          "rejected_value": "number"
        }, {
          "field_name": "/number-max",
          "error_message": "numeric instance is greater than the required maximum (maximum: 100, found: 1E+4)",
          "rejected_value": "1E+4"
        }, {
          "field_name": "/number-min",
          "error_message": "numeric instance is lower than the required minimum (minimum: 10, found: 1)",
          "rejected_value": "1"
        }, {
          "field_name": "/number-multof",
          "error_message": "remainder of division is not zero (100.001 / 0.01)",
          "rejected_value": "100.001"
        }, {
          "field_name": "/string-max",
          "error_message": "string \"fffffff\" is too long (length: 7, maximum allowed: 2)",
          "rejected_value": "fffffff"
        }, {
          "field_name": "/string-min",
          "error_message": "string \"f\" is too short (length: 1, required minimum: 5)",
          "rejected_value": "f"
        }, {
          "field_name": "/string-pattern",
          "error_message": "ECMA 262 regex \"^[A-Za-z0-9]+$\" does not match input string \"pi*lot\"",
          "rejected_value": "pi*lot"
        }]
      }
    */
  @Test
  def schemaValidationFailure1(): Unit = {
    val url = "http://localhost:" + port + "/schema_test1"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    val errorResponse = verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "GET", "JSON Schema validation errors encountered.")

    val someExpectedFieldErrors = List(
      new FieldError("/int-min", "17", "numeric instance is lower than the required minimum (minimum: 50, found: 17)"),
      new FieldError("/string-pattern", "pi*lot", """ECMA 262 regex "^[A-Za-z0-9]+$" does not match input string "pi*lot""""),
      new FieldError("/string-min",  "f", "string \"f\" is too short (length: 1, required minimum: 5)")
    )
    verifyFieldErrors(13, errorResponse, someExpectedFieldErrors)
  }

  @Test
  def schemaValidationFailureOnPost(): Unit = {
    val json =
      """{
        |  "name": "f",
        |  "age": 17,
        |  "job": "pi*lot"
        |}
      """.stripMargin

    val url = "http://localhost:" + port + "/create_person1"
    val responseEntity = restTemplate.postForEntity(url, json, classOf[String])
    val errorResponse = verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, "POST", "JSON Schema validation errors encountered.")

    val expectedFieldErrors = List(
      new FieldError("/age", "17", "numeric instance is lower than the required minimum (minimum: 18, found: 17)"),
      new FieldError("/job", "pi*lot", """ECMA 262 regex "^[A-Za-z0-9]+$" does not match input string "pi*lot""""),
      new FieldError("/name",  "f", "string \"f\" is too short (length: 1, required minimum: 2)")
    )
    verifyFieldErrors(3, errorResponse, expectedFieldErrors)
  }

  @Test
  def schemaValidationSuccessOnPost(): Unit = {
    val json =
      """{
        |  "name": "Fred",
        |  "age": 22,
        |  "job": "Artist"
        |}
      """.stripMargin

    val url = "http://localhost:" + port + "/create_person1"
    val responseEntity = restTemplate.postForEntity(url, json, classOf[String])
    Assert.assertEquals("valid", responseEntity.getBody)
    val headers: HttpHeaders = responseEntity.getHeaders
    Assert.assertEquals(TEXT_PLAIN, headers.get(CONTENT_TYPE).toString)
  }
}


