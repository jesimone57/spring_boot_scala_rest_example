package com.jsimone.controller

import javax.servlet.http.HttpSession

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.TEXT_PLAIN_VALUE))
class HelloWorldController {

  @GetMapping(value = Array("/"))
  def hello() = {
    "Hello World"
  }

  @GetMapping(value = Array("/hello1"))
  def helloByRequestParam(@RequestParam(value = "name", required = false, defaultValue = "World") name: String) = {
    String.format("Hello, %s!", name)
  }

  @GetMapping(value = Array("/hello2/{name}"))
  def helloByPathVariable(@PathVariable name: String) = {
    "hello " + name
  }

}
