package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.github.fge.jackson.JsonLoader
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.{JsonSchema, JsonSchemaFactory}
import com.jsimone.error.ErrorResponseBody
import com.jsimone.exception.ErrorResponseException
import com.jsimone.util.{JsonUtil, Logging}
import org.springframework.beans.TypeMismatchException
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.{ExceptionHandler, ResponseStatus}
import org.springframework.web.bind.{MethodArgumentNotValidException, MissingPathVariableException, MissingServletRequestParameterException}

import scala.io.BufferedSource

class ControllerBase extends Logging {

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

  @ExceptionHandler(Array(classOf[ErrorResponseException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleErrorResponseException(exception: ErrorResponseException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  private def buildBadRequestResponse(exception: Exception, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    val status = HttpStatus.BAD_REQUEST
    val errorResponseBody = exception match {
      case e: ErrorResponseException => e.errorResponse
      case _ => new ErrorResponseBody(status.value, request, exception)
    }
    new ResponseEntity[AnyRef](JsonUtil.toJson(errorResponseBody), headers, status)
  }

  protected def jsonSchemaValidateFromResource(inputFilename: String, schemaFilename: String, request: HttpServletRequest) = {
    var inputJsonString: String = null
    try {
       val bufferedSource = scala.io.Source.fromResource(inputFilename)
       inputJsonString = bufferedSource.getLines().mkString
    } catch {
      case ioex: Exception => throw new ErrorResponseException(new ErrorResponseBody(HttpStatus.BAD_REQUEST.value, request, ioex))
    }
    jsonSchemaValidateFromString(inputJsonString, schemaFilename, request)
  }

  protected def jsonSchemaValidateFromString(inputString: String, schemaFilename: String, request: HttpServletRequest) = {
    var schemaJsonNode: JsonNode = null
    try {
      schemaJsonNode = JsonLoader.fromResource(schemaFilename)
    } catch {
      case ioex: Exception => throw new ErrorResponseException(new ErrorResponseBody(HttpStatus.BAD_REQUEST.value, request, ioex))
    }
    val objectMapper: ObjectMapper = new ObjectMapper()
    val inputJsonNode: JsonNode = objectMapper.readTree(inputString)
    val jsonSchemaFactory: JsonSchemaFactory = JsonSchemaFactory.byDefault()
    val jsonSchema: JsonSchema = jsonSchemaFactory.getJsonSchema(schemaJsonNode)

    val report: ProcessingReport = jsonSchema.validate(inputJsonNode)
    if (!report.isSuccess) throw new ErrorResponseException(
      new ErrorResponseBody(HttpStatus.BAD_REQUEST.value(), request, report))
  }

}
