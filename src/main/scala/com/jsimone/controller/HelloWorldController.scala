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
  def helloByRequestParam(@RequestParam(value = "name", required = false, defaultValue = "World") name: String) = {
    log.info("/hello1 endpoint hit.")
    "Hello, %s!".format(name)
  }

  @GetMapping(value = Array(UrlPath.HELLO2))
  def helloByPathVariable(@PathVariable name: String) = {
    log.info("/hello2 endpoint hit.")
    "hello %s".format(name)
  }

  @GetMapping(value = Array(UrlPath.HELLO3))
  def helloByRequestClass(person: Person) = {
    log.info("/hello3 endpoint hit with person params: %s".format(person.toString))
    "hello %s, whose age is %d and job is %s".format(person.name, person.age, person.job)
  }

  @GetMapping(value = Array(UrlPath.HELLO4))
  def helloByRequestClassValidate(@Valid person: Person) = {
    log.info("/hello4 endpoint hit with person params: %s".format(person.toString))
    "hello %s, whose age is %d and job is %s".format(person.name, person.age, person.job)
  }


}
