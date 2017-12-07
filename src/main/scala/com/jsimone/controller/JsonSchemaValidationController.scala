package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import com.jsimone.constants.UrlPath
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array(UrlPath.ROOT), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class JsonSchemaValidationController extends BaseController {

  @GetMapping(value = Array("/schema_test1"))
  def validateAgainstSchema(request: HttpServletRequest) = {

    log.info(s"${request.getMethod} method on endpoint ${request.getRequestURI} hit.")
    jsonSchemaValidateResource("test1.json", "/test1_schema.json", request)
    //val restTemplate: RestTemplate = new RestTemplate()
    //val url = "http://gturnquist-quoters.cfapps.io/api/random"
    //restTemplate.getForObject(url, classOf[String])
  }

}
