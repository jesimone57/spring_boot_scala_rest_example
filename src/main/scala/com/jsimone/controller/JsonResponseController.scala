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
class JsonResponseController extends ControllerBase {

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

}
