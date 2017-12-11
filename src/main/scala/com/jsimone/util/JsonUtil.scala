package com.jsimone.util

import java.io.ByteArrayOutputStream

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.jsimone.error.ErrorResponseBody

object JsonUtil {
  val objectMapper = new ObjectMapper() with ScalaObjectMapper
  objectMapper.registerModule(DefaultScalaModule)
  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

//  def toJson(value: Map[Symbol, Any]): String = {
//    toJson(value map { case (k, v) => k.name -> v })
//  }

  def toJson(value: Any): String = {
    //mapper.writeValueAsString(value)
    val out = new ByteArrayOutputStream()
    objectMapper.writeValue(out, value)
    out.toString
  }

//  def toMap[V](json: String)(implicit m: Manifest[V]) = fromJson[Map[String, V]](json)

  def fromJson[T](json: String)(implicit m: Manifest[T]): T = {
    objectMapper.readValue[T](json)
  }

//  def fromJson(json: String): ErrorResponseBody = {
//    objectMapper.readValue(json, classOf[ErrorResponseBody])
//  }
}

