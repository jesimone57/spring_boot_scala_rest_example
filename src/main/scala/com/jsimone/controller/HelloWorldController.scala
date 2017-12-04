package com.jsimone.controller

import javax.validation.Valid

import com.jsimone.entity.Person
import com.jsimone.util.Logging
import org.springframework.http.MediaType
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.TEXT_PLAIN_VALUE))
class HelloWorldController extends Logging {

  @GetMapping(value = Array("/"))
  def hello() = {
    log.info("/ endpoint hit.")
    "Hello World"
  }

  @GetMapping(value = Array("/hello1"))
  def helloByRequestParam(@RequestParam(value = "name", required = false, defaultValue = "World") name: String) = {
    log.info("/hello1 endpoint hit.")
    "Hello, %s!".format(name)
  }

  @GetMapping(value = Array("/hello2/{name}"))
  def helloByPathVariable(@PathVariable name: String) = {
    log.info("/hello2 endpoint hit.")
    "hello %s".format(name)
  }

  @GetMapping(value = Array("/hello3"))
  def helloByRequestClass(person: Person) = {
    log.info("/hello3 endpoint hit with person params: %s".format(person.toString))
    "hello %s, whose age is %d and job is %s".format(person.name, person.age, person.job)
  }

    @GetMapping(value = Array("/hello4"))
    def helloByRequestClassValidate(@Valid person: Person) = {
      log.info("/hello4 endpoint hit with person params: %s".format(person.toString))
      "hello %s, whose age is %d and job is %s".format(person.name, person.age, person.job)
    }

}
