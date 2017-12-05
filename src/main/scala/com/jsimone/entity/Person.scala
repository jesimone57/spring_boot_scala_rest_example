package com.jsimone.entity

import javax.validation.constraints.{Max, Min, NotNull, Size}

import org.hibernate.validator.constraints.NotBlank

import scala.beans.BeanProperty

class Person() {

  @Size(min=2, max=30)    // Note: we'll use the default message since we did not provide a custom message
  @BeanProperty var name: String = "none"

  @Min(value = 18, message = "Age should be a minium of 18")
  @Max(value = 150, message = "Age should be a maximum of 150")
  @BeanProperty var age: Int = 0

  @NotBlank
  @NotNull                // Note: we'll use the default message since we did not provide a custom message
  @BeanProperty var job: String = _

  override def toString: String = {
    super.toString
    "Person { name: \"%s\", age: \"%d\", job: \"%s\" }".format(name, age, job)
  }
}
