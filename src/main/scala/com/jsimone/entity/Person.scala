package com.jsimone.entity

import scala.beans.BeanProperty

class Person() {
  @BeanProperty var name: String = "none"
  @BeanProperty var age: Int = 0
  @BeanProperty var job: String = "unknown"

  override def toString: String = {
    super.toString
    "Person { name: \"%s\", age: \"%d\", job: \"%s\" }".format(name, age, job)
  }
}
