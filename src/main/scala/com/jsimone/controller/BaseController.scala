package com.jsimone.controller

import javax.servlet.http.HttpServletRequest

import com.jsimone.error.ErrorResponseBody
import com.jsimone.util.{JsonUtil, Logging}
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.{ExceptionHandler, ResponseStatus}

class BaseController extends Logging {

  @ExceptionHandler(Array(classOf[BindException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleBindException(exception: BindException, request: HttpServletRequest): ResponseEntity[AnyRef] = {
    log.error(exception.toString)
    val headers = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    val status = HttpStatus.BAD_REQUEST

    val errorResponseBody = new ErrorResponseBody(status.value, request, exception)

    new ResponseEntity[AnyRef](JsonUtil.toJson(errorResponseBody), headers, status)
  }
}
