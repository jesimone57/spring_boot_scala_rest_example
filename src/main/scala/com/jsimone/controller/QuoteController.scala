package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class QuoteController {

  @GetMapping(value = Array("/quote"))
  def validateAgainstSchema(request: HttpServletRequest): String = {
    val restTemplate: RestTemplate = new RestTemplate()
    val url = "http://gturnquist-quoters.cfapps.io/api/random"
    restTemplate.getForObject(url, classOf[String])
  }
}
