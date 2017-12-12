package com.jsimone.util

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.github.fge.jackson.JsonLoader
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.JsonSchemaFactory

object JsonValidator {
  lazy val mapper = new ObjectMapper

  lazy val jsonSchemaFactory = JsonSchemaFactory.byDefault
  lazy val personSchemaNode = JsonLoader.fromResource("/person_schema.json")
  lazy val personSchema = jsonSchemaFactory.getJsonSchema(personSchemaNode)

  def validateWithReport(json: String): ProcessingReport = {
    val node: JsonNode = mapper.readTree(mapper.getFactory.createParser(json.getBytes("utf-8")))
    personSchema.validate(node)
  }

  def validate(json: String): Boolean = {
    validateWithReport(json) match {
      case report if report.isSuccess => true
      case report => throw new RuntimeException(report.toString)
    }
  }
}
