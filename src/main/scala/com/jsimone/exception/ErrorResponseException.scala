package com.jsimone.exception

import com.jsimone.error.ErrorResponse

class ErrorResponseException extends Exception {

  var errorResponse:  ErrorResponse = _

  def this(errorResponse:  ErrorResponse) = {
    this()
    this.errorResponse = errorResponse
  }
}

