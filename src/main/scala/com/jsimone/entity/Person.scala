package com.jsimone.entity

import javax.validation.constraints._

import org.hibernate.validator.constraints.NotBlank

import scala.beans.BeanProperty

/**
    Standard JSR annotations:

    @NotNull – validates that the annotated property value is not null
    @AssertTrue – validates that the annotated property value is true
    @Size – validates that the annotated property value has a size between the attributes min and max; can be applied to String, Collection, Map, and array properties
    @Min – vValidates that the annotated property has a value no smaller than the value attribute
    @Max – validates that the annotated property has a value no larger than the value attribute
    @Email – validates that the annotated property is a valid email address
    @Pattern - validates using a regular expression

    Some annotations accept additional attributes, but the message attribute is common to all of them.
    This is the message that will usually be rendered when the value of the respective property fails validation.

    Some additional annotations that can be found in the JSR are:

    @NotEmpty – validates that the property is not null or empty; can be applied to String, Collection, Map or Array values
    @NotBlank – can be applied only to text values and validated that the property is not null or whitespace
    @Positive and @PositiveOrZero – apply to numeric values and validate that they are strictly positive, or positive including 0
    @Negative and @NegativeOrZero – apply to numeric values and validate that they are strictly negative, or negative including 0
    @Past and @PastOrPresent – validate that a date value is in the past or the past including the present; can be applied to date types including those added in Java 8
    @Future and @FutureOrPresent – validates that a date value is in the future, or in the future including the present

    The validation annotations can also be applied to elements of a collection:
*/
class Person() {

  @Size(min=2, max=30)    // Note: we'll use the default message since we did not provide a custom message
  @BeanProperty var name: String = "none"

  @Min(value = 18, message = "Age should be a minimum of 18")
  @Max(value = 150, message = "Age should be a maximum of 150")
  @BeanProperty var age: Int = 0

  @NotBlank   // Note: we'll use the default message since we did not provide a custom message
  @Pattern(regexp = "^[A-Za-z0-9]+$")   // Alpha numeric string
  @BeanProperty var job: String = _

  def this(name: String, age: Int, job: String) {
    this()
    this.name = name
    this.age = age
    this.job = job
  }

  override def toString: String = {
    s"${this.getClass.getSimpleName} { name: $name, age: $age, job: $job }"
  }

  private def canEqual(a: Any) = a.isInstanceOf[Person]

  override def equals(that: Any): Boolean =
    that match {
      case that: Person => that.canEqual(this) && this.hashCode == that.hashCode
      case _ => false
    }

  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + age
    result = prime * result + (if (name == null) 0 else name.hashCode)
    result = prime * result + (if (job == null) 0 else job.hashCode)
    result
  }
}
