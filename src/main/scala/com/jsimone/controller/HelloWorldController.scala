package com.jsimone.controller

import org.apache.log4j.Logger
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.TEXT_PLAIN_VALUE))
class HelloWorldController {

  val log: Logger = Logger.getLogger(classOf[HelloWorldController])

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

}
