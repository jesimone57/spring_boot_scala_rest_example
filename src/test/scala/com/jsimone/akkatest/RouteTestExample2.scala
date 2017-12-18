package com.jsimone.akkatest

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.server.Directives._

/**
  * Testing in Akka HTTP
  *
  * Akka HTTP puts a lot of focus on testability of code.
  * It has a dedicated module akka-http-testkit for testing rest api’s.
  * When you use this testkit you are not need to run external web server or application server to test your rest API’s.
  * It will do all needed the stubbing and mocking for you which greatly simplifies the testing process.
  *
  * @see <a href="http://blog.madhukaraphatak.com/akka-http-testing/">Akka HTTP Testing</a>
  * @see <a href="https://doc.akka.io/docs/akka-http/current/scala/http/routing-dsl/testkit.html">Route Testing DSL</a>
  */
// ScalatestRouteTest is a Trait that brings the akka-http
// tesing DSL into the scope of this class
class RouteTestExample2 extends WordSpec with Matchers with ScalatestRouteTest {

  // in actual apps, you would probably define your routes
  // elsewhere and import them here
  val smallRoute =
  get {
    pathSingleSlash {
      complete {
        "Hello World"
      }
    }~
      path("hello1") {
        complete("Hello, Fred")
      }
  }

  "The service" should {

    "return Hello World for GET requests to the root path" in {
      Get() ~> smallRoute ~> check {
        // parsing the reponse as a string and asserting
        responseAs[String] shouldEqual "Hello World"
      }
    }

    "return a 'Hello, <name>' response for GET requests to /hello1" in {
      Get("/hello1?name=fred") ~> smallRoute ~> check {
        responseAs[String] shouldEqual "Hello, Fred"
      }
    }

  }
}
