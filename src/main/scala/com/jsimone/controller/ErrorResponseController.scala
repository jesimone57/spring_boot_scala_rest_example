package com.jsimone.controller

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import com.jsimone.constants.UrlPath
import com.jsimone.entity.Person
import com.jsimone.error.{ErrorResponseBody, FieldError}
import com.jsimone.util.JsonUtil
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array(UrlPath.ROOT), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class ErrorResponseController extends BaseController {

  @GetMapping(value = Array(UrlPath.EXAMPLE_RESPONSE))
  def sampleErrorResponse(request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.info(s"${request.getMethod} method on endpoint ${request.getRequestURI} hit.")

    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    val status = HttpStatus.BAD_REQUEST
    val errorResponseBody = new ErrorResponseBody(status.value(), request.getRequestURI, "some weird error")
    errorResponseBody.errors += new FieldError("name", "", "required name is empty or null")
    errorResponseBody.errors += new FieldError("age", "0", "invalid age given")
    errorResponseBody.errors += new FieldError("phone", "123", "invald phone number provided")
    new ResponseEntity[AnyRef](JsonUtil.toJson(errorResponseBody), headers, HttpStatus.BAD_REQUEST)
  }

  /**
    * See ref https://stackoverflow.com/questions/12113010/scala-responsebody-and-map
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

  @GetMapping(value = Array(UrlPath.THROWN_EXCEPTION))
  def throwingAnException(): String = {
    log.info("/thrown_exception enpoint hit.")
    throw new IllegalArgumentException("Illegal Argument Exception")
  }

  // Note:  this endpoint works with validation because the exception handler is in the base class
  @GetMapping(value = Array("/validate_person"))
  def helloByRequestClassValidate(@Valid person: Person): String = {
    log.info("/validate_person endpoint hit with person params: %s".format(person.toString))
    s"Hello ${person.name}, whose age is ${person.age} and job is ${person.job}"
  }

}
