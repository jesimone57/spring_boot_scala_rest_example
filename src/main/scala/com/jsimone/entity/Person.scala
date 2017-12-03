package com.jsimone.entity

import javax.validation.constraints.{Max, Min}

import scala.beans.BeanProperty

class Person() {
  @BeanProperty var name: String = "none"

  @Min(value = 18, message = "Age should not be less than 18")
  @Max(value = 150, message = "Age should not be greater than 150")
  @BeanProperty var age: Int = 0
  @BeanProperty var job: String = "unknown"

  override def toString: String = {
    super.toString
    "Person { name: \"%s\", age: \"%d\", job: \"%s\" }".format(name, age, job)
  }
}
