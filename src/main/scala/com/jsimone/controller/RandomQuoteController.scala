package com.jsimone.controller

import javax.validation.Valid

import com.jsimone.constants.UrlPath
import com.jsimone.entity.Person
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping(value = Array(UrlPath.ROOT), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class RandomQuoteController extends BaseController {

  @GetMapping(value = Array("/quote"))
  def getQuote() = {
    log.info("/quote endpoint hit.")
    val restTemplate: RestTemplate = new RestTemplate()
    val url = "http://gturnquist-quoters.cfapps.io/api/random"
    restTemplate.getForObject(url, classOf[String])
  }

}
