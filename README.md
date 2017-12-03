# Example Showing How to Implement REST API using Spring Boot and Scala

This repository contains examples of how to use the Spring REST framework to implement 
an API which does some simple things:
1. Say hello
2. Specific exception handler for exceptions of a given class
3. A global exception handler as a catch-all for all other exceptions
4. Convert Scala object responses to JSON
5. A template for standardizing error responses via ErrorResponseBody and FieldError classes.


NOTE:
If you try to hit an invalid endpoint, the Spring controller advice will produce a
nice error message indicating the endpoint is not in service.

## Prerequisites needed to compile, test and execute the code
* scala 2.11.*
* java 1.8
* gradle 4 or higher
* git 

## To execute the code
1. git clone https://github.com/jesimone57/spring_boot_scala_rest_example.git
2. gradle build
3. gradle bootRun
4. For best results, use Google Chrome and install the JSONView chrome extension to nicely format JSON results.
Firefox browser also does an excellent job of formatting the JSON results without the need for an extension.
5. Try any of the following URLs (Note: Spring Boot runs Tomcat server on localhost port 8080):

### Check to make sure the REST API is running
[http://localhost:8080/health](http://localhost:8080/health)

## Hello Example URL end-points

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

### Hello from request parameter mapping onto Person entity - using named parameters
[http://localhost:8080/hello3?name=Elvis&age=45&job=Singer](http://localhost:8080/hello3?name=Elvis&age=45&job=Singer)

## Hello from request parameter mapping onto Person entity - using named parameters with validation -> 400 bad request
[http://localhost:8080/hello4?name=Elvis&age=45&job=Singer](http://localhost:8080/hello4?name=Elvis&age=12&job=Singer)

### Example error response
[http://localhost:8080/example_response](http://localhost:8080/example_response)

### Sample JSON response of a nested Scala object
[http://localhost:8080/json](http://localhost:8080/json)

### Example of an arbitrarily thrown exception caught by an exception handler
[http://localhost:8080//thrown_exception](http://localhost:8080//thrown_exception)

