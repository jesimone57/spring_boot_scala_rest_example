package com.jsimone.error

import javax.servlet.http.HttpServletRequest

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.fge.jsonschema.core.report.ProcessingReport
import org.springframework.validation.{BindException, BindingResult}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class ErrorResponseBody() {
  @JsonProperty("status_code") var code: Int = _
  @JsonProperty("uri_path") var path: String = _
  @JsonProperty("method") var method: String = _
  @JsonProperty("error_message") var message: String = _
  @JsonProperty("errors") var errors: ListBuffer[FieldError] = ListBuffer()

  def this(code: Int, path: String, message: String) = {
    this()
    this.code = code
    this.path = path
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
    this.message = if (report.isSuccess) "" else s"JSON Schema validation errors encountered."
    this.method = request.getMethod
    setSchemaValidationProcessingMessageErrors(report)
  }

  def this(code: Int, path: String, message: String, errors: ListBuffer[FieldError]) = {
    this()
    this.code = code
    this.path = path
    this.message = message
    this.errors = errors
  }

  private def addBindingResultErrors(bindingResult: BindingResult): Unit = {
    bindingResult
      .getFieldErrors
      .forEach(fe => this.errors += new FieldError(fe.getField,
        if (fe.getRejectedValue != null) fe.getRejectedValue.toString else null,
        fe.getDefaultMessage))
  }

  /**
    * @see <a href="https://stackoverflow.com/questions/20702855/how-can-we-extract-all-messages-from-processing-report-in-json-schema-validator">
    *        how-can-we-extract-all-messages-from-processing-report-in-json-schema-validator</a>
    * @param report Json schema validation report
    */
  private def setSchemaValidationProcessingMessageErrors(report: ProcessingReport): Unit = {
    errors = ListBuffer() ++ report.iterator().asScala.map { pm =>
      val rejectedValue = pm.asJson.get("keyword").asText() match {
        case "minimum" | "maximum" | "type" =>
          pm.asJson.get("found").asText()
        case "pattern" =>
          pm.asJson.get("string").asText()
        case "enum" | "format" | "minLength" | "maxLength" | "multipleOf" =>
          pm.asJson.get("value").asText()
        case "additionalProperties" =>
          pm.asJson.get("unwanted").asText()
        case "required" =>
          pm.asJson.get("missing").asText()
        case unknown =>
          s"Unknown schema keyword $unknown encountered"
      }
      new FieldError(
        pm.asJson.get("instance").elements().next().asText(),
        rejectedValue,
        pm.asJson.get("message").asText()
      )
    }
  }

  override def toString: String = {
    super.toString
    s"ErrorResponseBody { code: $code, path: $path, message: $message, errors: [${errors.toString()}] }"
  }
}
