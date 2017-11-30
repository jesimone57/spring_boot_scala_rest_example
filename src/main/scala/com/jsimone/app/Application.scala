package com.jsimone.app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


object Application {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[Application], args: _*) // conversion to Java varargs
  }
}

@SpringBootApplication
class Application {}

