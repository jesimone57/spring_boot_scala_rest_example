package com.jsimone.error

import com.fasterxml.jackson.annotation.JsonProperty

class FieldError() {
  @JsonProperty("error_field") var field: String = _
  @JsonProperty("error_value")var value: String = _
  @JsonProperty("error_message") var message: String = _

  def this(field: String, value: String, message: String) = {
    this()
    this.field = field
    this.value = value
    this.message = message
  }

  override def toString: String = {
    super.toString
    "FieldError { field: \"%s\", value: \"%s\", message: \"%s\" }".format( field, value, message )
  }
}