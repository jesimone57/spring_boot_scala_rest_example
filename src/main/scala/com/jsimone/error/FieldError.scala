package com.jsimone.error

import com.fasterxml.jackson.annotation.JsonProperty

class FieldError() {
  @JsonProperty("field_name") var field: String = _
  @JsonProperty("rejected_value")var value: String = _
  @JsonProperty("error_message") var message: String = _

  def this(field: String, rejectedValue: String, message: String) = {
    this()
    this.field = field
    this.value = rejectedValue
    this.message = message
  }

  override def toString: String = {
    super.toString
    "FieldError { field: \"%s\", value: \"%s\", message: \"%s\" }".format( field, value, message )
  }
}
