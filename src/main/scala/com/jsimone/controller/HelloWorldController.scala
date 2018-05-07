package com.jsimone.controller

import com.jsimone.entity.Person
import javax.validation.Valid
import javax.validation.constraints.{NotNull, Size}
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class HelloWorldController extends AbstractControllerBase {

    @GetMapping(value = Array("/"))
    def hello(): String = {
        "Hello World"
    }

    /**
      *
      * Note: the param will be optional.  Only if @RequestParam annotation is used will the
      * default required value = true be applied to the  param if not specified.
      * See the next example.
      */
    @GetMapping(value = Array("/hello0"))
    def helloByDefaultRequestParam(name: String): String = {
        s"Hello, $name"
    }

    /**
      * Note: the default required value = true if not specified.
      * If you want an optional request param you must specify required = false
      */
    @GetMapping(value = Array("/hello1"))
    def helloByOptionalRequestParam(@RequestParam(value = "name", required = false, defaultValue = "World") name: String): String = {
        s"Hello, $name"
    }

    @GetMapping(value = Array("/hello1.1"))
    def helloByRequiredRequestParam(@RequestParam(value = "name", required = true) name: String): String = {
        s"Hello, $name"
    }

    @GetMapping(value = Array("/hello1.2"))
    def helloByRequiredRequestParamAsInt(@RequestParam(value = "num", required = true) num: Int): String = {
        s"Hello, $num"
    }

    @GetMapping(value = Array("/hello2/{name}"))
    def helloByPathVariable(@PathVariable name: String): String = {
        s"Hello, $name"
    }

    @GetMapping(value = Array("/hello2.1/{num}"))
    def helloByPathVariableAsInt(@PathVariable num: Int): String = {
        s"Hello, $num"
    }

    @GetMapping(value = Array("/hello3"))
    def helloByRequestClass(person: Person): String = {
        s"Hello ${person.name}, whose age is ${person.age} and job is ${person.job}"
    }

    /**
      * Invokes validation using @Valid for all parameters bound into the Person object
      */
    @GetMapping(value = Array("/hello4"))
    def helloByRequestClassValidate(@Valid person: Person): String = {
        s"Hello ${person.name}, whose age is ${person.age} and job is ${person.job}"
    }

    /**
      * Note:  This WON'T work.  Why?  @Valid looks at constraints inside an object and ignores
      * the @NotEmpty and @Size constraints as applied here.
      * You can leave off the @RequestParam, the results will be the same since @RequestParam is assume by Spring
      */
    @GetMapping(value = Array("/hello5"))
    def helloByRequestClassValidate(@NotNull @Size(min = 2, max = 30) @RequestParam(value = "name") @Valid name: String): String = {
        s"Hello $name"
    }

}
