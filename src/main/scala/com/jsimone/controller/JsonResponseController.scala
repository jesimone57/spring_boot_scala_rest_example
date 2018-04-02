package com.jsimone.controller

import com.jsimone.util.JsonUtil
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class JsonResponseController extends AbstractControllerBase {

  /**
    * See ref https://stackoverflow.com/questions/12113010/scala-Response-and-map
    */
  @GetMapping(value = Array("/json"))
  def mapToJson(): String = {
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
