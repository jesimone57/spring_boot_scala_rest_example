package com.jsimone.exception

import com.jsimone.error.ErrorResponse

class ErrorResponseException extends RuntimeException {

  var errorResponse:  ErrorResponse = _

  def this(errorResponse:  ErrorResponse) = {
    this()
    this.errorResponse = errorResponse
  }
}

