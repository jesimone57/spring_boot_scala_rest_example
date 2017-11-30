package com.jsimone.app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
  * This is the Spring boot entrypoint class.
  * At runtime, this class boots the Spring application and starts the spring context.
  * The Object keyword is used to create a singleton instance of the class in Scala.
  */
@SpringBootApplication
class Application

object Application extends App {
  SpringApplication.run(classOf[Application])
}


