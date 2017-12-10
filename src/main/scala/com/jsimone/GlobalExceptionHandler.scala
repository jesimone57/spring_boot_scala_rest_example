package com.jsimone

import javax.servlet.http.HttpServletRequest

import com.jsimone.error.ErrorResponseBody
import com.jsimone.util.{JsonUtil, Logging}
import org.springframework.http._
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler}
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


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
  */
@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler with Logging {

  @ExceptionHandler(Array(classOf[Exception]))
  protected def handleDefaultException(exception: Exception, request: WebRequest): ResponseEntity[AnyRef] = {
    val path = request.getDescription(false).substring(4)
    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    val message = Option(exception.getMessage).getOrElse(exception.getStackTrace.mkString("\n"))
    val status = HttpStatus.INTERNAL_SERVER_ERROR
    val errorResponseBody = new ErrorResponseBody(status.value, path, message)
    handleExceptionInternal(exception, JsonUtil.toJson(errorResponseBody), headers, status, request)
  }

  /**
    * Note:  we must add the 2 properties to resources/application.properties in order for this to work:
    *   spring.mvc.throw-exception-if-no-handler-found=true
    *   spring.resources.add-mappings=false
    * Note:  The application properties file must reside in /src/main/resources or it will not be found by Spring Boot
    */
  override protected def handleNoHandlerFoundException(exception: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity[AnyRef] = {
    val path = request.getDescription(false).substring(4)
    headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE)
    val message = "The URL you have reached is not in service at this time"
    val errorResponseBody = new ErrorResponseBody(status.value, path, message)
    handleExceptionInternal(exception, JsonUtil.toJson(errorResponseBody), headers, status, request)
    //new ResponseEntity[AnyRef](errorResponseBody, status)
  }
}