package com.jsimone.exception

import javax.servlet.http.HttpServletRequest

import com.jsimone.error.ErrorResponse
import com.jsimone.util.{JsonUtil, Logging}
import org.springframework.beans.TypeMismatchException
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
import org.springframework.validation.BindException
import org.springframework.web.bind.{MethodArgumentNotValidException, MissingPathVariableException, MissingServletRequestParameterException}
import org.springframework.web.bind.annotation.{ExceptionHandler, ResponseStatus}

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

  @ExceptionHandler(Array(classOf[Exception]))
  protected def handleDefaultException(exception: Exception, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    val path = request.getRequestURI
    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    //val message = Option(exception.getMessage).getOrElse(exception.getStackTrace.mkString("\n"))
    val message = exception.getStackTrace.mkString("\n")
    val status = HttpStatus.INTERNAL_SERVER_ERROR
    val errorResponse = new ErrorResponse(status.value, path, message)
    errorResponse.method = request.getMethod
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
