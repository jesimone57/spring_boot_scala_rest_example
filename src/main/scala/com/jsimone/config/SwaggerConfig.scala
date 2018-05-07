package com.jsimone.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.servlet.config.annotation.{ResourceHandlerRegistry, WebMvcConfigurationSupport}
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.{ApiInfoBuilder, RequestHandlerSelectors}
import springfox.documentation.service.{ApiInfo, Contact}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    def api(): Docket = {
        new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.jsimone.controller"))
            .paths(regex(".*"))
            .build()
            .apiInfo(metaData)
    }

    override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    private def metaData: ApiInfo = {
        return new ApiInfoBuilder()
            .title("Spring Boot Scala REST API Application Example")
            .description(
                """A sample Scala project using Spring Boot and providing examples of validation and end-point testing.
        for more information on the OpenAPI Specification (aka Swagger) used herein see
        <a href=http://openapi-specification-visual-documentation.apihandyman.io/>OpenAPI Visual Documentation</a>""")
            .version("1.0.0")
            .license("Apache License Version 2.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
            .contact(new Contact("Joseph Simone", "http://github.com/jesimone57", "jesimone57@yahoo.com"))
            .build()
    }
}
