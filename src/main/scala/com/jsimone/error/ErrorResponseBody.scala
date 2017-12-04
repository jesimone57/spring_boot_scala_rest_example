package com.jsimone.error

import com.fasterxml.jackson.annotation.JsonProperty

import scala.collection.mutable.ListBuffer

class ErrorResponseBody() {
  @JsonProperty("status_code") var code: Int = _
  @JsonProperty("uri_path") var path: String = _
  @JsonProperty("method") var method: String = _
  @JsonProperty("error_message") var message: String = _
  @JsonProperty("errors") var errors: ListBuffer[FieldError] = ListBuffer()

  def this(code: Int, path: String, message: String) = {
    this()
    this.code = code
    this.path = path
    this.message = message
  }

  def this(code: Int, path: String, message: String, errors: ListBuffer[FieldError]) = {
    this()
    this.code = code
    this.path = path
    this.message = message
    this.errors = errors
  }

  override def toString: String = {
    super.toString
    "ErrorResponseBody { code: \"%d\", path: \"%s\", message: \"%s\", errors: [%s] }".format(code, path, message,
      errors.toString())
  }
}
