package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import com.jsimone.constants.UrlPath
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array(UrlPath.ROOT), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class JsonSchemaValidationController extends BaseController {

  @GetMapping(value = Array("/schema_test1"))
  def validateAgainstSchema(request: HttpServletRequest) = {
    log.info(s"${request.getMethod} method on endpoint ${request.getRequestURI} hit.")
    jsonSchemaValidateResource("test1.json", "/test1_schema.json", request)
  }

  /**
    * Example http://localhost:8080/schema?input=test1.json&schema=/test1_schema.json
    */
  @GetMapping(value = Array("/schema"))
  def validateNamedFileWithNamedSchema(
                                        @RequestParam(value = "input", required = true) inputFilename: String,
                                        @RequestParam(value = "schema", required = true) schemaFilename: String,
                                        request: HttpServletRequest) = {
    log.info(s"${request.getMethod} method on endpoint ${request.getRequestURI} hit.")
    jsonSchemaValidateResource(inputFilename, schemaFilename, request)
  }

}
