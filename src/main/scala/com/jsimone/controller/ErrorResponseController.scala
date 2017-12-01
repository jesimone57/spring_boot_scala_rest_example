package com.jsimone.controller

import com.jsimone.error.{ErrorResponseBody, FieldError}
//import com.jsimone.util.JsonUtil
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class ErrorResponseController {

  @GetMapping(value = Array("/example_response"))
  def sampleErrorResponse(): ErrorResponseBody = {
    val errorResponseBody = new ErrorResponseBody(400, "http://asdf", "some weird error")
    errorResponseBody.errors += new FieldError("name", "",  "required name is empty or null")
    errorResponseBody.errors += new FieldError("age", "0",  "invalid age given")
    errorResponseBody.errors += new FieldError("phone", "123",  "invald phone number provided")
    errorResponseBody
    //JsonUtil.toJson(errorResponseBody)  // crashes : org.springframework.web.HttpMediaTypeNotAcceptableException: Could not find acceptable representation
  }

  @GetMapping(value = Array("/thrown_exception"))
  def throwingAnException(): String = {
    throw new IllegalArgumentException("Illegal Argument Exception")
    "never get here"
  }

}
