package com.jsimone.error

import javax.servlet.http.HttpServletRequest

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.fge.jsonschema.core.report.ProcessingReport
import org.springframework.http.HttpStatus
import org.springframework.validation.{BindException, BindingResult}

import scala.collection.mutable.ListBuffer

class ErrorResponseBody() {
  @JsonProperty("status_code") var code: Int = _
  @JsonProperty("uri_path") var path: String = _
  @JsonProperty("method") var method: String = _
  @JsonProperty("error_message") var message: String = _
  @JsonProperty("errors") var errors: ListBuffer[FieldError] = ListBuffer()

  def this(code: Int, uri: String, message: String) = {
    this()
    this.code = code
    this.path = uri
    this.message = message
  }

  def this(code: Int, request: HttpServletRequest, exception: Exception) = {
    this()
    this.code = code
    this.path = request.getRequestURI
    this.message = exception.getMessage
    this.method = request.getMethod
    exception match {
      case e: BindException => this.addBindingResultErrors(e.getBindingResult)
      case _ =>

    }
  }

  def this(code: Int, request: HttpServletRequest, report: ProcessingReport) = {
    this()
    this.code = code
    this.path = request.getRequestURI
    this.message = ""
    this.method = request.getMethod

    import collection.JavaConverters._

    // see https://stackoverflow.com/questions/20702855/how-can-we-extract-all-messages-from-processing-report-in-json-schema-validator
    errors = ListBuffer() ++ report.iterator().asScala.map{ pm =>
      val rejectedValue = pm.asJson().get("keyword").asText() match {
        case "minimum" => pm.asJson().get("found").asText()
        case "maximum" => pm.asJson().get("found").asText()
        case "pattern" => pm.asJson().get("string").asText()
        case "minLength" => pm.asJson().get("value").asText()
        case "maxLength" => pm.asJson().get("value").asText()
        case "additionalProperties" => pm.asJson().get("unwanted").asText()
        case "required" => pm.asJson().get("missing").asText()
        case unknown => s"Unknown schema keyword $unknown encountered"
      }
      new FieldError(
        pm.asJson().get("instance").elements().next().textValue(),
        rejectedValue,
        pm.asJson().get("message").asText()
      )
    }
//    val itr = report.iterator()
//    itr.hasNext
//    val pm = itr.next()
//    pm.asJson().get("instance").elements().next().textValue()
//    pm.asJson().get("found").asText()
//    pm.asJson().get("message").asText()
//    pm.asJson()
//    report.forEach(message => message.asJson().get("instance").asText())
    //this.addBindingResultErrors(exception.getBindingResult)
  }

  def this(code: Int, path: String, message: String, errors: ListBuffer[FieldError]) = {
    this()
    this.code = code
    this.path = path
    this.message = message
    this.errors = errors
  }

  private def addBindingResultErrors(bindingResult: BindingResult) = {
    bindingResult
      .getFieldErrors
      .forEach(fe => this.errors +=  new FieldError(fe.getField,
        if (fe.getRejectedValue != null) fe.getRejectedValue.toString else null,
        fe.getDefaultMessage))
  }

  override def toString: String = {
    super.toString
    "ErrorResponseBody { code: \"%d\", path: \"%s\", message: \"%s\", errors: [%s] }".format(code, path, message,
      errors.toString())
  }
}
