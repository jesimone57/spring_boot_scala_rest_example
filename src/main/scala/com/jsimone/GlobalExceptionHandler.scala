package com.jsimone

import javax.validation.ConstraintViolation

import com.jsimone.util.JsonUtil
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  import com.jsimone.error.ErrorResponseBody
  import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
  import org.springframework.web.bind.annotation.ExceptionHandler
  import org.springframework.web.context.request.WebRequest
  import org.springframework.web.servlet.NoHandlerFoundException

  @ExceptionHandler(Array(classOf[Exception]))
  protected def handleDefaultException(exception: RuntimeException, request: WebRequest): ResponseEntity[AnyRef] = {
    val URIPath = request.getDescription(false).substring(4)
    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    val message = exception.getMessage
    val status = HttpStatus.INTERNAL_SERVER_ERROR
    val errorResponseBody = new ErrorResponseBody(status.value, URIPath, message)
    handleExceptionInternal(exception, JsonUtil.toJson(errorResponseBody), headers, status, request)
  }

//  import org.springframework.http.HttpStatus
//  import org.springframework.validation.BindingResult
//  import org.springframework.web.bind.MethodArgumentNotValidException
//  import org.springframework.web.bind.annotation.ExceptionHandler
//  import org.springframework.web.bind.annotation.ResponseBody
//  import org.springframework.web.bind.annotation.ResponseStatus
//
//  @ExceptionHandler(Array(classOf[MethodArgumentNotValidException]))
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  def handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException, request: WebRequest): ResponseEntity[AnyRef] = {
//    val URIPath = request.getDescription(false).substring(4)
//    val headers = new HttpHeaders
//    headers.setContentType(MediaType.APPLICATION_JSON)
//    val message = exception.getMessage
//    val status = HttpStatus.BAD_REQUEST
//    val result = exception.getBindingResult
//    val fieldErrors = result.getFieldErrors
//    val errorResponseBody = new ErrorResponseBody(status.value, URIPath, message)  // todo: set field errors into response body
//    handleExceptionInternal(exception, JsonUtil.toJson(errorResponseBody), headers, status, request)
//  }

  /**
    * Note:  we must add the 2 properties to resources/application.properties in order for this to work:
    *   spring.mvc.throw-exception-if-no-handler-found=true
    *   spring.resources.add-mappings=false
    * Note:  The application properties file must reside in /src/main/resources or it will not be found by Spring Boot
    */
  override protected def handleNoHandlerFoundException(exception: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity[AnyRef] = {
    val URIPath = request.getDescription(false).substring(4)
    val message = "The URL you have reached is not in service at this time"
    val errorResponseBody = new ErrorResponseBody(status.value, URIPath, message)
    handleExceptionInternal(exception, JsonUtil.toJson(errorResponseBody), headers, status, request)
    //new ResponseEntity[AnyRef](errorResponseBody, status)
  }
}