package com.jsimone.error

import com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty

class FieldError() {

  @BeanProperty @JsonProperty("field_name") var field: String = _
  @BeanProperty @JsonProperty("rejected_value") var value: String = _
  @BeanProperty @JsonProperty("error_message") var message: String = _

  def this(field: String, rejectedValue: String, message: String) = {
    this()
    this.field = field
    this.value = rejectedValue
    this.message = message
  }

  override def toString: String = {
    super.toString
    "FieldError { field: \"%s\", value: \"%s\", message: \"%s\" }".format(field, value, message)
  }

  private def canEqual(a: Any) = a.isInstanceOf[FieldError]

  override def equals(that: Any): Boolean =
    that match {
      case that: FieldError => that.canEqual(this) && this.hashCode == that.hashCode
      case _ => false
    }

  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + (if (field == null) 0 else field.hashCode)
    result = prime * result + (if (value == null) 0 else value.hashCode)
    result = prime * result + (if (message == null) 0 else message.hashCode)
    result
  }
}
