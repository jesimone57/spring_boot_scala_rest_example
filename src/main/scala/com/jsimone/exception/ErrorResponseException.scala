package com.jsimone.exception

import com.jsimone.error.ErrorResponseBody

class ErrorResponseException extends Exception {

  var errorResponse:  ErrorResponseBody = _

  def this(errorResponse:  ErrorResponseBody) = {
    this()
    this.errorResponse = errorResponse
  }
}

