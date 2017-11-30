package com.jsimone.controller

import org.springframework.web.bind.annotation._

@RestController
//@RequestMapping(Array("/"))
class HelloWorldController {

  @RequestMapping(value = Array("/helloworld"), method = Array(RequestMethod.GET))
  def sayHelloWorld = {
    "hello world"
  }

  @RequestMapping(value = Array("/hello1"), method = Array(RequestMethod.GET))
  def sayHelloByParameter(@RequestParam("name") name: String) = {
    "hello " + name
  }

  @RequestMapping(value = Array("/hello2/{name}"), method = Array(RequestMethod.GET))
  def sayHelloByVariable(@PathVariable name: String) = {
    "hello " + name
  }

}
