package com.jsimone.controller

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import com.jsimone.constants.UrlPath
import com.jsimone.entity.Person
import com.jsimone.error.{ErrorResponse, FieldError}
import com.jsimone.exception.ErrorResponseException
import com.jsimone.util.JsonUtil
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
    errorResponse.errors += new FieldError("phone", "123", "invald phone number provided")
    throw new ErrorResponseException(errorResponse)
  }

  /**
    * See ref https://stackoverflow.com/questions/12113010/scala-Response-and-map
    */
  @GetMapping(value = Array(UrlPath.JSON))
  def mapToJson(): String = {
    log.info("/json endpoint hit.")
    val m = Map(
      "name" -> "john doe",
      "age" -> 18,
      "hasChild" -> true,
      "childs" -> List(
        Map("name" -> "dorothy", "age" -> 5, "hasChild" -> false),
        Map("name" -> "bill", "age" -> 8, "hasChild" -> false)))

    JsonUtil.toJson(m)
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

  // Note:  this endpoint works with validation because the exception handler is in the base class
  @GetMapping(value = Array("/validate_person"))
  def helloByRequestClassValidate(@Valid person: Person): String = {
    log.info("/validate_person endpoint hit with person params: %s".format(person.toString))
    s"Hello ${person.name}, whose age is ${person.age} and job is ${person.job}"
  }

}
