package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.github.fge.jackson.JsonLoader
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.{JsonSchema, JsonSchemaFactory}
import com.jsimone.error.ErrorResponse
import com.jsimone.exception.{ErrorResponseException, MyExceptionHandler}
import com.jsimone.util.Logging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin

/**
  * @see <a href="https://spring.io/guides/gs/rest-service-cors/">Enabling Cross Origin Requests for a RESTful Web Service</a>
  */
@CrossOrigin(origins = Array("*"))
class ControllerBase extends MyExceptionHandler with Logging {

  def jsonSchemaValidateFromResource(inputFilename: String, schemaFilename: String, request: HttpServletRequest): Unit = {
    val inputNode: JsonNode = readResourceAsJsonNode(inputFilename, request)
    val schemaNode: JsonNode = readResourceAsJsonNode(schemaFilename, request)
    jsonSchemaValidate(inputNode, schemaNode, request)
  }

  def jsonSchemaValidateFromString(jsonString: String, schemaFilename: String, request: HttpServletRequest): Unit = {
    val objectMapper: ObjectMapper = new ObjectMapper()
    val inputJsonNode: JsonNode = objectMapper.readTree(jsonString)
    val schemaNode: JsonNode = readResourceAsJsonNode(schemaFilename, request)
    jsonSchemaValidate(inputJsonNode, schemaNode, request)
  }

  protected def jsonSchemaValidate(inputNode: JsonNode, schemaNode: JsonNode, request: HttpServletRequest): Unit = {
    val jsonSchemaFactory: JsonSchemaFactory = JsonSchemaFactory.byDefault()
    val jsonSchema: JsonSchema = jsonSchemaFactory.getJsonSchema(schemaNode)

    val report: ProcessingReport = jsonSchema.validate(inputNode)
    if (!report.isSuccess) throw new ErrorResponseException(
      new ErrorResponse(HttpStatus.BAD_REQUEST.value(), request, report))
  }

  protected def readResourceAsJsonNode(resourceFilename: String, request: HttpServletRequest): JsonNode = {
    try {
      JsonLoader.fromResource(resourceFilename)
    } catch {
      case ioex: Exception => throw new ErrorResponseException(new ErrorResponse(HttpStatus.BAD_REQUEST.value, request, ioex))
    }
  }

}
