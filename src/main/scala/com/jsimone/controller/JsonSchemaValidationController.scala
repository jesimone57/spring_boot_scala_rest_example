package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.TEXT_PLAIN_VALUE))
class JsonSchemaValidationController extends AbstractControllerBase {

  @GetMapping(value = Array("/schema_test2"))
  def validateAgainstSchema(request: HttpServletRequest): String = {
    jsonSchemaValidateFromResource("/test2.json", "/test2_schema.json", request)
    "valid"
  }


  @PostMapping(value = Array("/create_person1"))
  def createPerson1(@RequestBody requestBody: String, request: HttpServletRequest): String = {
    jsonSchemaValidateFromString(requestBody, "/person1_schema.json", request)
    "valid"
  }

  /**
    * Example http://localhost:8080/schema?input=test1.json&schema=/test1_schema.json
    */
  @GetMapping(value = Array("/schema"))
  def validateNamedFileWithNamedSchema(
                                        @RequestParam(value = "input", required = true) inputFilename: String,
                                        @RequestParam(value = "schema", required = true) schemaFilename: String,
                                        request: HttpServletRequest): String = {
    jsonSchemaValidateFromResource(inputFilename, schemaFilename, request)
    "valid"
  }

}
