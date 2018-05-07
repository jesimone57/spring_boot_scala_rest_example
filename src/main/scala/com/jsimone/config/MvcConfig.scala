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

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.servlet.config.annotation.{EnableWebMvc, WebMvcConfigurer}

@Configuration
@EnableWebMvc
class MvcConfig extends WebMvcConfigurer {

    @Bean
    def requestLoggingFilterRegistration: FilterRegistrationBean[_] = {
        val filterRegistrationBean = new FilterRegistrationBean
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.setName("requestLoggingFilter")
        filterRegistrationBean.setOrder(1)
        filterRegistrationBean
    }

    @Bean
    def jsonSchemaValidationFilterRegistration: FilterRegistrationBean[_] = {
        val filterRegistrationBean = new FilterRegistrationBean
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.setName("jsonSchemaValidationFilter")
        filterRegistrationBean.setOrder(2)
        filterRegistrationBean
    }

}
