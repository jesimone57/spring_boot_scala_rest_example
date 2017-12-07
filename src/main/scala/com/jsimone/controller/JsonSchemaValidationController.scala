package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.github.fge.jackson.JsonLoader
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.{JsonSchema, JsonSchemaFactory}
import com.jsimone.constants.UrlPath
import com.jsimone.error.ErrorResponseBody
import com.jsimone.util.JsonUtil
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array(UrlPath.ROOT), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class JsonSchemaValidationController extends BaseController {

  @GetMapping(value = Array("/schema"))
  def validateAgainstSchema(request: HttpServletRequest) = {
    log.info("/schema endpoint hit.")

    val json = scala.io.Source.fromResource("person1.json").getLines().mkString
    val jsonSchemaNode: JsonNode = JsonLoader.fromResource("/person1_schema.json")

    val objectMapper: ObjectMapper = new ObjectMapper()
    val actualObject: JsonNode = objectMapper.readTree(json)
    val jsonSchemaFactory: JsonSchemaFactory = JsonSchemaFactory.byDefault()
    val jsonSchema: JsonSchema = jsonSchemaFactory.getJsonSchema(jsonSchemaNode)
    val report: ProcessingReport = jsonSchema.validate(actualObject)
    if (report.isSuccess) {
      "ok"
    } else {
      JsonUtil.toJson(new ErrorResponseBody(400, request, report))
    }
    //val restTemplate: RestTemplate = new RestTemplate()
    //val url = "http://gturnquist-quoters.cfapps.io/api/random"
    //restTemplate.getForObject(url, classOf[String])
  }

}
