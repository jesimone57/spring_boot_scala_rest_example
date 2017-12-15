package com.jsimone.controller

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import com.jsimone.constants.UrlPath
import com.jsimone.entity.Person
import com.jsimone.error.{ErrorResponse, FieldError}
import com.jsimone.exception.ErrorResponseException
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array(UrlPath.ROOT), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class ErrorResponseController extends ControllerBase {

  @GetMapping(value = Array(UrlPath.ERROR_RESPONSE))
  def sampleErrorResponse(request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.info(s"${request.getMethod} method on endpoint ${request.getRequestURI} hit.")

    val errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), request.getRequestURI, "example request validation errors")
    errorResponse.method = request.getMethod
    errorResponse.errors += new FieldError("name", "", "required name is empty or null")
    errorResponse.errors += new FieldError("age", "0", "invalid age given")
    errorResponse.errors += new FieldError("phone", "123", "invalid phone number provided")
    throw new ErrorResponseException(errorResponse)
  }

  @GetMapping(value = Array(UrlPath.THROWN_EXCEPTION1))
  def throwingAnException1(): String = {
    log.info("/thrown_exception1 endpoint hit.")
    throw new IllegalArgumentException("Illegal Argument Exception")
  }

  @GetMapping(value = Array(UrlPath.THROWN_EXCEPTION2))
  def throwingAnException2(): String = {
    log.info("/thrown_exception2 endpoint hit.")
    throw new NullPointerException()
  }

}
