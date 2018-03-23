package com.jsimone.controller

import com.jsimone.AbstractTestBase
import com.jsimone.error.FieldError
import org.junit.runner.RunWith
import org.junit.{Assert, Test}
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.{HttpHeaders, HttpMethod, HttpStatus, MediaType}
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JsonSchemaValidationControllerTest extends AbstractTestBase {

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
    val url = s"http://localhost:$port/schema"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "Required String parameter 'input' is not present")
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
    val url = s"http://localhost:$port/schema?schema=/person1.json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "Required String parameter 'input' is not present")
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
    val url = s"http://localhost:$port/schema?input=/person1.json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "Required String parameter 'schema' is not present")
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
    val url = s"http://localhost:$port/schema?input=person1.json&schema=person1_schema.json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "resource path does not start with a '/'")
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
    val url = s"http://localhost:$port/schema?input=/person1.json&schema=person1_schema.json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "resource path does not start with a '/'")
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
    val url = s"http://localhost:$port/schema?input=person1.json&schema=/person1_schema.json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "resource path does not start with a '/'")
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
    val url = s"http://localhost:$port/schema?input=/person1.json&schema=/person1_schema.json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    val errorResponse = verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "JSON Schema validation errors encountered.")

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
            "uri_path": "/schema_test2",
            "method": "GET",
            "error_message": "JSON Schema validation errors encountered.",
            "errors": [{
                "field_name": "",
                "error_message": "the following keywords are unknown and will be ignored: [propertyNames]",
                "rejected_value": ""
            }, {
                "field_name": "",
                "error_message": "object instance has properties which are not allowed by the schema: [\"extra-prop\"]",
                "rejected_value": ""
            }, {
                "field_name": "",
                "error_message": "object has missing required properties ([\"required\"])",
                "rejected_value": ""
            }]
        }
    */
  @Test
  def schemaValidationFailure1(): Unit = {
    val url = s"http://localhost:$port/schema_test2"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    val errorResponse = verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.GET, "JSON Schema validation errors encountered.")

    val someExpectedFieldErrors = List(
      new FieldError("", "", "the following keywords are unknown and will be ignored: [propertyNames]"),
      new FieldError("", "", "object instance has properties which are not allowed by the schema: [\"extra-prop\"]"),
      new FieldError("",  "", "object has missing required properties ([\"required\"])")
    )
    verifyFieldErrors(3, errorResponse, someExpectedFieldErrors)
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

    val url = s"http://localhost:$port/create_person1"
    val responseEntity = restTemplate.exchange(url, HttpMethod.POST, buildHttpEntity(json), classOf[String])
    val errorResponse = verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.POST, "JSON Schema validation errors encountered.")

    val expectedFieldErrors = List(
      new FieldError("/age", "17", "numeric instance is lower than the required minimum (minimum: 18, found: 17)"),
      new FieldError("/job", "pi*lot", """ECMA 262 regex "^[A-Za-z0-9]+$" does not match input string "pi*lot""""),
      new FieldError("/name",  "f", "string \"f\" is too short (length: 1, required minimum: 2)")
    )
    verifyFieldErrors(3, errorResponse, expectedFieldErrors)
  }

  /**
    {
      "status_code": 400,
      "uri_path": "/create_person1",
      "method": "POST",
      "error_message": "Required request body is missing: public java.lang.String com.jsimone.controller.JsonSchemaValidationController.createPerson1(java.lang.String,javax.servlet.http.HttpServletRequest)",
      "errors": [],
    }
    */
  @Test
  def schemaValidationFailureOnPostMissingJSON(): Unit = {
    val json = ""

    val url = s"http://localhost:$port/create_person1"
    val responseEntity = restTemplate.exchange(url, HttpMethod.POST, buildHttpEntity(json), classOf[String])
    val errorResponse = verifyErrorResponse(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.POST,
      "Required request body is missing: public java.lang.String com.jsimone.controller.JsonSchemaValidationController.createPerson1(java.lang.String,javax.servlet.http.HttpServletRequest)")
  }

  /**
    {
      "status_code": 400,
      "uri_path": "/create_person1",
      "method": "POST",
      "error_message": "Unexpected end-of-input: expected close marker for Object (start marker at [Source: { "name": "Fred", "age": 22, "job": "Artist" ; line: 1, column: 1]) at [Source: { "name": "Fred", "age": 22, "job": "Artist" ; line: 5, column: 56]",
      "errors": [],
    }
    */
  @Test
  def schemaValidationFailureOnPostJSONException1(): Unit = {
    val json =
      """{
        |  "name": "f",
        |  "age": 17,
        |  "job": "pi*lot"
      """.stripMargin

    val url = s"http://localhost:$port/create_person1"
    val responseEntity = restTemplate.exchange(url, HttpMethod.POST, buildHttpEntity(json), classOf[String])
    val errorResponse = verifyErrorResponsePrefix(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.POST,
      "Unexpected end-of-input: expected close marker for Object (start marker at [Source:")
  }

  /**
    {
      "status_code": 400,
      "uri_path": "/create_person1",
      "method": "POST",
      "error_message": "Unexpected character ('A' (code 65)): was expecting a colon to separate field name and value at [Source: { "name": "Fred", "age": 22, "job: "Artist" }; line: 4, column: 11]",
      "errors": [],
    }
    */
  @Test
  def schemaValidationFailureOnPostJSONException2(): Unit = {
    val json =
      """{
        |  "name": "Fred",
        |  "age": 22,
        |  "job: "Artist"
        |}
      """.stripMargin

    val url = s"http://localhost:$port/create_person1"
    val responseEntity = restTemplate.exchange(url, HttpMethod.POST, buildHttpEntity(json), classOf[String])
    val errorResponse = verifyErrorResponsePrefix(responseEntity, HttpStatus.BAD_REQUEST, HttpMethod.POST,
      "Unexpected character ('A' (code 65)): was expecting a colon to separate field name and value")
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

    val url = s"http://localhost:$port/create_person1"
    val responseEntity = restTemplate.exchange(url, HttpMethod.POST, buildHttpEntity(json), classOf[String])

    // verifySuccessResponse(responseEntity, HttpStatus.OK, MediaType.TEXT_PLAIN, "valid")  // TODO: fix this

    Assert.assertEquals("valid", responseEntity.getBody)
    val headers: HttpHeaders = responseEntity.getHeaders
    Assert.assertEquals(TEXT_PLAIN, headers.get(CONTENT_TYPE).toString)
  }
}


