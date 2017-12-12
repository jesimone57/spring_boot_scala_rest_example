package com.jsimone.util

import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.TimeZone

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
//import org.json4s._
//import org.json4s.jackson.JsonMethods._
//import org.json4s.jackson.Serialization.{read,write}


/**
  * Other serialization/deserialization featuress include:
  *
  * // Serialization features of Jackson (converting object to json)
  * objectMapper.enable(SerializationFeature.INDENT_OUTPUT)    // enable this for indented output
  * objectMapper.setSerializationInclusion(Include.NON_NULL)   // enable this to ignore null fields
  * objectMapper.setSerializationInclusion(Include.NON_EMPTY)  // enable this to ignore empty arrays
  * val fmt: SimpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy")   // custom definition for date format
  * objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
  *
  * // deserialization features of Jackson (converting json to object)
  * objectMapper.setDateFormat(fmt)
  * objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
  *
  */
object JsonUtil {
  //implicit val formats = DefaultFormats

  val W3C_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
  val W3C_UTC_DATE_FORMAT = new SimpleDateFormat(W3C_UTC_FORMAT)
  W3C_UTC_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"))

  val objectMapper = new ObjectMapper() with ScalaObjectMapper
  objectMapper.registerModule(DefaultScalaModule)
  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  objectMapper.setDateFormat(W3C_UTC_DATE_FORMAT)  // set a custom date format, or just take the default

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

  /*
    Json4s implementation.   I could not get this to work.

  def toJson2(value: AnyRef): String = {
    write(value)
  }

  def fromJson2[T](json: String)(implicit m: Manifest[T]): T = {
    read[T](json)
   // or
    parse(json).extract[T]
  }
  */
}

