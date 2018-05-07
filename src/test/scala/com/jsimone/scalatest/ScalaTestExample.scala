package com.jsimone.scalatest

import org.scalatest._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort

// Trait `Matchers` enables constructs like `should be()`
class ScalaTestExample extends FlatSpec with Matchers {

    @LocalServerPort
    protected val port: Int = 0

    @Autowired
    protected val restTemplate: TestRestTemplate = null

    "An empty Set" should "have size 0" in {
        assert(Set.empty.size == 0)
    }

    "A List" should "return values in order" in {
        val list = List(3, -1, 10)
        assert(list.length === 3)
        assert(list(1) === 3)
        assert(list(2) === -1)
        assert(list(3) === 10)
    }

    it should "throw IndexOutOfBoundsException if we get an index for a non-existing element" in {
        val list = List(3, -1, 10)
        a[IndexOutOfBoundsException] should be thrownBy {
            list(4)
        }
    }
}
