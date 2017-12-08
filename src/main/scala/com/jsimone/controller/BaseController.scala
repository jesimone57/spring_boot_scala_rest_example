package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.github.fge.jackson.JsonLoader
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.{JsonSchema, JsonSchemaFactory}
import com.jsimone.error.ErrorResponseBody
import com.jsimone.util.{JsonUtil, Logging}
import org.springframework.beans.TypeMismatchException
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
import org.springframework.validation.BindException
import org.springframework.web.bind.{MethodArgumentNotValidException, MissingPathVariableException, MissingServletRequestParameterException}
import org.springframework.web.bind.annotation.{ExceptionHandler, ResponseStatus}

class BaseController extends Logging {

  @ExceptionHandler(Array(classOf[BindException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected def handleBindException(exception: BindException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[MissingServletRequestParameterException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleMissingServletRequestParameterException(exception: MissingServletRequestParameterException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[TypeMismatchException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleTypeMismatchException(exception: TypeMismatchException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[MethodArgumentNotValidException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[MissingPathVariableException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleMissingPathVariableException(exception: MissingPathVariableException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  private def buildBadRequestResponse( exception: Exception, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    val status = HttpStatus.BAD_REQUEST
    val errorResponseBody = new ErrorResponseBody(status.value, request, exception)
    new ResponseEntity[AnyRef](JsonUtil.toJson(errorResponseBody), headers, status)
  }

  protected def jsonSchemaValidateResource(inputFilename: String, schemaFilename: String, request: HttpServletRequest) = {
    var jsonString: String = ""
    try {
      jsonString = scala.io.Source.fromResource(inputFilename).getLines().mkString
    } catch {
      case ioe: Exception => new ErrorResponseBody(HttpStatus.BAD_REQUEST.value(), request, ioe)
    }

    val schemaJsonNode: JsonNode = JsonLoader.fromResource(schemaFilename)

    val objectMapper: ObjectMapper = new ObjectMapper()
    val inputJsonNode: JsonNode = objectMapper.readTree(jsonString)
    val jsonSchemaFactory: JsonSchemaFactory = JsonSchemaFactory.byDefault()
    val jsonSchema: JsonSchema = jsonSchemaFactory.getJsonSchema(schemaJsonNode)

    val report: ProcessingReport = jsonSchema.validate(inputJsonNode)
    if (report.isSuccess) {
      "valid"
    } else {
      JsonUtil.toJson(new ErrorResponseBody(HttpStatus.BAD_REQUEST.value(), request, report))
    }
  }
}
