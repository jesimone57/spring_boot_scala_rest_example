package com.jsimone.exception

import com.jsimone.error.ErrorResponse
import com.jsimone.util.{JsonUtil, Logging}
import org.springframework.http._
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler with Logging {

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
    val errorResponse = new ErrorResponse(status.value, path, null, message)
    handleExceptionInternal(exception, JsonUtil.toJson(errorResponse), headers, status, request)
    //new ResponseEntity[AnyRef](errorResponse, status)
  }
}