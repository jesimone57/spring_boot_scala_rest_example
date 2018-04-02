package com.jsimone.filter

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
class RequestLoggingFilter extends Filter with Logging {

    override def init(fConfig: FilterConfig): Unit = {
        log.info("RequestLoggingFilter initialized")
    }

    override def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
        log.info("RequestLoggingFilter doFilter")
        val req = request.asInstanceOf[HttpServletRequest]

        log.info(s"${req.getMethod} method on endpoint ${req.getRequestURI} hit (from ${req.getRemoteHost})")
        val params = req.getParameterNames
        while (params.hasMoreElements) {
            val name = params.nextElement
            val value = request.getParameter(name)
            log.info(s"Request Param: {$name=$value}")
        }
        val headersEnumeration = req.getHeaderNames
        while (headersEnumeration.hasMoreElements) {
            val name = headersEnumeration.nextElement()
            val value = req.getHeader(name)
            log.info(s"Header: {$name=$value}")
        }
        val cookies = req.getCookies
        if (cookies != null) for (cookie <- cookies) {
            log.info(req.getRemoteAddr + "::Cookie::{" + cookie.getName + "," + cookie.getValue + "}")
        }

        // pass the request along the filter chain
        chain.doFilter(request, response)
    }

    override def clone(): AnyRef = super.clone()

    override def destroy(): Unit = {
        //we can close resources here
    }
}