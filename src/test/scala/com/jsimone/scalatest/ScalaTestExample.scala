package com.jsimone.scalatest

import org.scalatest._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate

import scala.collection.mutable.Stack

// Trait `Matchers` enables constructs like `should be()`
class ScalaTestExample extends FlatSpec with Matchers  {

  @LocalServerPort
  protected val port: Int = 0

  @Autowired
  protected val restTemplate: TestRestTemplate = null

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[String]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }
}