package com.jsimone.controller

import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(path = Array("/"))
class HelloWorldController {

  //@RequestMapping(value = Array("/helloworld"), method = Array(RequestMethod.GET))
  @GetMapping(path = Array("/helloworld"))
  def sayHelloWorld = {
    val x = "Hello World"
    x
  }

  @RequestMapping(value=Array("/greeting"))
  def greeting(@RequestParam(value="name", required=false, defaultValue="World") name: String)  = {
    String.format("Hello, %s!", name)
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
