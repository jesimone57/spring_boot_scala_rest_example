# Example Showing How to Implement REST API using Spring Boot and Scala

This repository contains examples of how to use the Spring REST framework to implement 
an API which does some simple things:
1. Say hello
2. Specific exception handlers for exceptions of a given class
3. A global exception handler as a catch-all for all other exceptions
4. Convert Scala object responses to JSON
5. A template for standardizing error responses via ErrorResponse and FieldError classes.
6. JSON schema validation
7. Request paramater validation via constraint annotations and @Valid


NOTE:
If you try to hit an invalid endpoint, the Spring controller advice will produce a
nice error message indicating the endpoint is not in service.

## Prerequisites needed to compile, test and execute the code
* java 1.8+
* gradle 4+
* git 

## To execute the code
1. git clone https://github.com/jesimone57/spring_boot_scala_rest_example.git
2. gradle build
3. gradle bootRun
4. For best results, use Google Chrome and install the JSONView chrome extension to nicely format JSON results.
Firefox browser also does an excellent job of formatting the JSON results without the need for an extension.
5. Try any of the following URLs (Note: Spring Boot runs Tomcat server on localhost port 8080 by default.
You can change the port in application.properties file):

### Check to make sure the REST API is running
[http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

### Swagger API Documentation
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Swagger API Docs JSON Format
[http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs)

### Hello World
[http://localhost:8080](http://localhost:8080)

### Hello World using a default name
[http://localhost:8080/hello1](http://localhost:8080/hello1)

### Hello World using a given name as a request parameter
[http://localhost:8080/hello1?name=Frank](http://localhost:8080/hello1?name=Frank)

### Hello using a name as a path variable
[http://localhost:8080/hello2/tom](http://localhost:8080/hello2/tom)

### Hello from request parameter mapping onto Person entity - using all defaults
[http://localhost:8080/hello3](http://localhost:8080/hello3)

### Hello from request parameters mapping onto Person entity - using named parameters
[http://localhost:8080/hello3?name=Elvis&age=45&job=Singer](http://localhost:8080/hello3?name=Elvis&age=45&job=Singer)

### Hello from request parameter mapping onto Person entity - using named parameters with validation -> 400 bad request: age field constraint violation
[http://localhost:8080/hello4?name=Elvis&age=12&job=Singer](http://localhost:8080/hello4?name=Elvis&age=12&job=Singer)

### Hello from request parameters mapping onto Person entity - using named parameters with validation -> 400 bad request: age and name field constraint violations
[http://localhost:8080/hello4?name=f&age=17&job=Painter](http://localhost:8080/hello4?name=f&age=17&job=Painter)

### Hello from request parameters mapping onto Person entity - using named parameters with validation -> 400 bad request: age, name and job field constraint violations. Null job field.
[http://localhost:8080/hello4?name=f&age=17](http://localhost:8080/hello4?name=f&age=17)

### Hello from request parameters mapping onto Person entity - using named parameters with validation -> 400 bad request: age, name and job field constraint violations. Empty job field.
[http://localhost:8080/hello4?name=f&age=17&job=](http://localhost:8080/hello4?name=f&age=17&job=)

### Hello from request parameters mapping onto Person entity - using named parameters with validation -> 400 bad request: job field violates regexp pattern constraint
[http://localhost:8080/hello4?name=Oscar&age=21&job=*actor](http://localhost:8080/hello4?name=Oscar&age=21&job=*actor)

### Example error response
[http://localhost:8080/error_response](http://localhost:8080/error_response)

### Sample JSON response of a nested Scala object
[http://localhost:8080/json](http://localhost:8080/json)

### Example of an arbitrarily thrown exception caught by an exception handler
[http://localhost:8080/thrown_exception1](http://localhost:8080/thrown_exception1)

### Example of 404 - page not found - due to invalid endpoint
[http://localhost:8080/asdf](http://localhost:8080/asdf)

### Example of invalid JSON against a JSON schema
[http://localhost:8080/schema?input=/person1.json&schema=/person1_schema.json](http://localhost:8080/schema?input=/person1.json&schema=/person1_schema.json)

### Example of valid JSON against a JSON schema (Note: schemas and input JSON are in the resources directory)
[http://localhost:8080/schema?input=/person2.json&schema=/person2_schema.json](http://localhost:8080/schema?input=/person2.json&schema=/person2_schema.json)


