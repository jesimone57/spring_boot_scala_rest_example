package com.jsimone.exception

import javax.servlet.http.HttpServletRequest

import com.fasterxml.jackson.core.JsonProcessingException
import com.jsimone.error.ErrorResponse
import com.jsimone.util.{JsonUtil, Logging}
import org.springframework.beans.TypeMismatchException
import org.springframework.http._
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.{MethodArgumentNotValidException, MissingPathVariableException, MissingServletRequestParameterException}

/**
  * Spring MVC Exceptions see https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-rest-spring-mvc-exceptions
  * *
  * BindException	                           400 (Bad Request)
  * ConversionNotSupportedException	         500 (Internal Server Error)
  * HttpMediaTypeNotAcceptableException	     406 (Not Acceptable)
  * HttpMediaTypeNotSupportedException	     415 (Unsupported Media Type)
  * HttpMessageNotReadableException	         400 (Bad Request)
  * HttpMessageNotWritableException	         500 (Internal Server Error)
  * HttpRequestMethodNotSupportedException	 405 (Method Not Allowed)
  * MethodArgumentNotValidException	         400 (Bad Request)
  * MissingServletRequestParameterException	 400 (Bad Request)
  * MissingServletRequestPartException	     400 (Bad Request)
  * NoSuchRequestHandlingMethodException	   404 (Not Found)
  * TypeMismatchException	                   400 (Bad Request)
  *
  */
class MyExceptionHandler extends Logging {

  @ExceptionHandler(Array(classOf[BindException]))
  protected def handleBindException(exception: BindException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[MissingServletRequestParameterException]))
  def handleMissingServletRequestParameterException(exception: MissingServletRequestParameterException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[TypeMismatchException]))
  def handleTypeMismatchException(exception: TypeMismatchException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[MethodArgumentNotValidException]))
  def handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[HttpMessageNotReadableException]))
  def handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[JsonProcessingException]))
  def handleJsonProcessingException(exception: JsonProcessingException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[MissingPathVariableException]))
  def handleMissingPathVariableException(exception: MissingPathVariableException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[ErrorResponseException]))
  def handleErrorResponseException(exception: ErrorResponseException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    buildBadRequestResponse(exception, request)
  }

  @ExceptionHandler(Array(classOf[Exception]))
  protected def handleDefaultException(exception: Exception, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    val path = request.getRequestURI
    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    //val message = Option(exception.getMessage).getOrElse(exception.getStackTrace.mkString("\n"))
    val message = exception.getStackTrace.mkString("\n")
    val status = HttpStatus.INTERNAL_SERVER_ERROR
    val errorResponse = new ErrorResponse(status.value, path, HttpMethod.valueOf(request.getMethod), message)
    new ResponseEntity[AnyRef](JsonUtil.toJson(errorResponse), headers, status)
  }

  private def buildBadRequestResponse(exception: Exception, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    val status = HttpStatus.BAD_REQUEST
    val errorResponse = exception match {
      case e: ErrorResponseException => e.errorResponse
      case _ => new ErrorResponse(status.value, request, exception)
    }
    new ResponseEntity[AnyRef](JsonUtil.toJson(errorResponse), headers, status)
  }
}

