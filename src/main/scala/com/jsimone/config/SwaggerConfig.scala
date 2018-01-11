package com.jsimone.config

import java.util.ArrayList

import org.springframework.context.annotation.{Bean, Configuration}
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.{PathSelectors, RequestHandlerSelectors}
import springfox.documentation.service.{ApiInfo, Contact, VendorExtension}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {
  @Bean
  def api(): Docket = {
    new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.jsimone.controller"))
      .paths(regex("/*"))
      .build()
      .apiInfo(metaData)
  }

  private def metaData: ApiInfo = {
    new ApiInfo(
      "Spring Boot Scala REST API Application Example",
      """A sample Scala project using Spring Boot and providing examples of validation and end-point testing.
        for more information on the OpenAPI Specification (aka Swagger) used herein see
        <a href=http://openapi-specification-visual-documentation.apihandyman.io/>OpenAPI Visual Documentation</a>""",
      "1.0.0",
      "Terms of service",
      new Contact("Joseph Simone", "http://github.com/jesimone57", "jesimone57@yahoo.com"),
      "Apache License Version 2.0",
      "https://www.apache.org/licenses/LICENSE-2.0")
      //new ArrayList[VendorExtension[_]])
  }
}
