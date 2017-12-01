package com.jsimone.controller

import com.jsimone.error.{ErrorResponseBody, FieldError}
import com.jsimone.util.{JsonUtil, Logging}
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class ErrorResponseController extends Logging {

  @GetMapping(value = Array("/example_response"))
  def sampleErrorResponse(): String = {
    log.info("/example_response endpoint hit.")
    val errorResponseBody = new ErrorResponseBody(400, "http://asdf", "some weird error")
    errorResponseBody.errors += new FieldError("name", "", "required name is empty or null")
    errorResponseBody.errors += new FieldError("age", "0", "invalid age given")
    errorResponseBody.errors += new FieldError("phone", "123", "invald phone number provided")
    JsonUtil.toJson(errorResponseBody)
  }

  /**
    * See ref https://stackoverflow.com/questions/12113010/scala-responsebody-and-map
    */
  @GetMapping(value = Array("/json"))
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

  @GetMapping(value = Array("/thrown_exception"))
  def throwingAnException(): String = {
    log.info("/thrown_exception enpoint hit.")
    throw new IllegalArgumentException("Illegal Argument Exception")
  }

}
