package com.jsimone.controller

import javax.validation.Valid

import com.jsimone.constants.UrlPath
import com.jsimone.entity.Person
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array(UrlPath.ROOT), produces = Array(MediaType.TEXT_PLAIN_VALUE))
class HelloWorldController extends BaseController {

  @GetMapping(value = Array(UrlPath.ROOT))
  def hello() = {
    log.info("/ endpoint hit.")
    "Hello World"
  }

  @GetMapping(value = Array(UrlPath.HELLO1))
  def helloByOptionalRequestParam(@RequestParam(value = "name", required = false, defaultValue = "World") name: String): String = {
    log.info("/hello1 endpoint hit.")
    s"Hello, $name"
  }

  @GetMapping(value = Array("/hello1.1"))
  def helloByRequiredRequestParam(@RequestParam(value = "name", required = true) name: String): String = {
    log.info("/hello1.1 endpoint hit.")
    s"Hello, $name"
  }

  @GetMapping(value = Array("/hello1.2"))
  def helloByRequiredRequestParamAsInt(@RequestParam(value = "num", required = true) num: Int): String = {
    log.info("/hello1.2 endpoint hit.")
    s"Hello, $num"
  }

  @GetMapping(value = Array(UrlPath.HELLO2))
  def helloByPathVariable(@PathVariable name: String): String = {
    log.info("/hello2 endpoint hit.")
    s"Hello, $name"
  }

  /**
    * This example is provided for illustration purposes only.
    * It will never work if coded this way that a path variable is defined as an int.
    * Note:  if you try, you will not be able to execute a valid endpoint as the
    * path variable is always expected to be a string.
    */
  @GetMapping(value = Array("/hello2.1"))
  def helloByPathVariableAsInt(@PathVariable num: Int): String = {
    log.info("/hello2.1 endpoint hit.")
    s"Hello, $num"
  }

  @GetMapping(value = Array(UrlPath.HELLO3))
  def helloByRequestClass(person: Person): String = {
    log.info("/hello3 endpoint hit with person params: %s".format(person.toString))
    s"Hello ${person.name}, whose age is ${person.age} and job is ${person.job}"
  }

  @GetMapping(value = Array(UrlPath.HELLO4))
  def helloByRequestClassValidate(@Valid person: Person): String = {
    log.info("/hello4 endpoint hit with person params: %s".format(person.toString))
    s"Hello ${person.name}, whose age is ${person.age} and job is ${person.job}"
  }

}
