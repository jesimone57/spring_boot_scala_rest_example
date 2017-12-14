package com.jsimone

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
  * This is the Spring Boot entry-point class.
  * At runtime, this class boots the Spring application and starts the Spring context.
  * The object keyword is used to create a singleton instance of the class in Scala.
  */
@SpringBootApplication
class Application

object Application extends App {
  SpringApplication.run(classOf[Application], args:_*)
}


