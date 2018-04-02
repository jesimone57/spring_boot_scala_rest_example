//package com.jsimone.config
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.servlet.config.annotation.EnableWebMvc
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
//
//@Configuration
//@EnableWebMvc
//class MvcConfig extends WebMvcConfigurerAdapter {
//  override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
//    registry
//      .addResourceHandler("**/**").addResourceLocations("classpath:/META-INF/resources/")
//      //.addResourceHandler("/resources/**")
//      //.addResourceLocations("/resources/", "/swagger-ui.html")
//  }
//}

import com.jsimone.filter.{JsonSchemaValidationFilter, RequestLoggingFilter}
import javax.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    def requestLoggingFilter: Filter = new RequestLoggingFilter

    @Bean
    def requestLoggingFilterRegistration = {
        val filterRegistrationBean = new FilterRegistrationBean
        filterRegistrationBean.setFilter(requestLoggingFilter)
        filterRegistrationBean.addUrlPatterns("/*")
        //filterRegistrationBean.addInitParameter("paramName", "paramValue")
        filterRegistrationBean.setName("requestLoggingFilter")
        filterRegistrationBean.setOrder(1)
        filterRegistrationBean
    }

    @Bean
    def jsonSchemaValidationFilter: Filter = new JsonSchemaValidationFilter

    @Bean
    def jsonSchemaValidationFilterRegistration = {
        val filterRegistrationBean = new FilterRegistrationBean
        filterRegistrationBean.setFilter(jsonSchemaValidationFilter)
        filterRegistrationBean.addUrlPatterns("/*")
        //filterRegistrationBean.addInitParameter("paramName", "paramValue")
        filterRegistrationBean.setName("jsonSchemaValidationFilter")
        filterRegistrationBean.setOrder(2)
        filterRegistrationBean
    }

}
