package com.jsimone.filter

import java.util.stream.Collectors

import com.jsimone.util.Logging
import javax.servlet._
import javax.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

/**
  * Servlet Filter implementation class RequestLoggingFilter
  * from https://www.journaldev.com/1933/java-servlet-filter-example-tutorial
  * from  https://stackoverflow.com/questions/19825946/how-to-add-a-filter-class-in-spring-boot
  */
@Component
class JsonSchemaValidationFilter extends Filter with Logging {

    override def init(fConfig: FilterConfig): Unit = {
        log.info("JsonSchemaValidationFilter initialized")
    }

    override def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
        log.info("JsonSchemaValidationFilter doFilter")
        val req = request.asInstanceOf[HttpServletRequest]

        val supportedMethods = List("POST", "PUT", "PATCH" )
        if (req.getHeader("content-type") == "application/json" && supportedMethods.contains(req.getMethod)) {
            log.info("---->can do schema validation ....")
            log.info(s"---->content type ${req.getContentType}")

            // NOTE:  the request body must been consumed/read in only one place: either here or in the controller end-point
            //val body = req.getReader.lines().collect(Collectors.joining(System.lineSeparator()))
            //log.info(s"---->body = $body")
        }

        // pass the request along the filter chain
        chain.doFilter(request, response)
    }

    override def clone(): AnyRef = super.clone()

    override def destroy(): Unit = {
        //we can close resources here
    }
}