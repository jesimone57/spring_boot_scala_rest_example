package com.jsimone.error

import javax.servlet.http.HttpServletRequest

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.fge.jsonschema.core.report.ProcessingReport
import org.springframework.http.{HttpMethod, HttpStatus}
import org.springframework.validation.{BindException, BindingResult}
import org.springframework.web.bind.MethodArgumentNotValidException

import scala.beans.BeanProperty
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class ErrorResponse() {
  @BeanProperty @JsonProperty("status_code") var code: Int = _
  @BeanProperty @JsonProperty("uri_path") var path: String = _
  @BeanProperty @JsonProperty("method") var method: HttpMethod = _
  @BeanProperty @JsonProperty("error_message") var message: String = _
  @BeanProperty @JsonProperty("errors") var errors: ListBuffer[FieldError] = ListBuffer()

  def this(code: Int, path: String, method: HttpMethod, message: String) = {
    this()
    this.code = code
    this.path = path
    this.method = method
    this.message = message
  }

  def this(code: Int, request: HttpServletRequest, exception: Exception) = {
    this()
    this.code = code
    this.path = Option(request).map(_.getRequestURI).getOrElse("")
    this.message = Option(exception).map(_.getMessage).getOrElse("")
    this.method = if (request != null) HttpMethod.valueOf(request.getMethod) else null
    exception match {
      case e: BindException => this.addBindingResultErrors(e.getBindingResult)
      case e:  MethodArgumentNotValidException => this.addBindingResultErrors(e.getBindingResult)
      case _ =>
    }
  }

  def this(code: Int, request: HttpServletRequest, report: ProcessingReport) = {
    this()
    this.code = code
    Option(request).foreach { r =>
      this.path = r.getRequestURI
      this.method = HttpMethod.valueOf(r.getMethod)
    }
    Option(report).foreach { _ =>
      this.message = if (report.isSuccess) "" else "JSON Schema validation errors encountered."
      setSchemaValidationProcessingMessageErrors(report)
    }
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
    *      how-can-we-extract-all-messages-from-processing-report-in-json-schema-validator</a>
    * @param report Json schema validation report
    */
  private def setSchemaValidationProcessingMessageErrors(report: ProcessingReport): Unit = {
    errors = ListBuffer() ++ report.iterator().asScala.map { pm =>
        val jsonNodeKeyword = pm.asJson.get("keyword")
        if (jsonNodeKeyword != null) {
            val rejectedValue = jsonNodeKeyword.asText() match {
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
        } else {
            new FieldError(
                pm.asJson.get("schema").asText(),
                "",
                pm.asJson.get("message").asText()
            )
        }

    }
  }

  override def toString: String =
    s"${this.getClass.getSimpleName} { code: $code, path: $path, message: $message, method: $method, errors: [${errors.toString()}] }"

  private def canEqual(a: Any) = a.isInstanceOf[ErrorResponse]

  override def equals(that: Any): Boolean =
    that match {
      case that: ErrorResponse => that.canEqual(this) && this.hashCode == that.hashCode
      case _ => false
    }

  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + code
    result = prime * result + (if (path == null) 0 else path.hashCode)
    result = prime * result + (if (method == null) 0 else method.hashCode)
    result = prime * result + (if (message == null) 0 else message.hashCode)
    result = prime * result + (if (errors == null) 0 else errors.hashCode)
    result
  }


}
