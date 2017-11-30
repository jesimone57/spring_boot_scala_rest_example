# Example Showing How to Implement REST API using Spring Boot and Scala

This repository contains examples of how to use the Spring REST framework to implement 
an API which does some simple things:
1. Say hello


NOTE:
If you try to hit an invalid endpoint, the Spring controller advice will produce a
nice error message indicating the endpoint is not in service.

## Prerequisites needed to compile, test and execute the code
* java 1.8
* gradle 4 or higher
* git 

## To execute the code
1. git clone https://github.com/jesimone57/spring_boot_scala_rest_example.git
2. gradle build
3. gradle bootRun
4. For best results, use Google Chrome and install the JSONView chrome plugin to nicely format JSON results. 
Firefox browser also does an excellent job of formatting the JSON results.
5. Then try any of the following URLs (Note: Spring Boot runs Tomcat server on localhost port 8080):

### Check to make sure the webservice is running
[http://localhost:8080/health](http://localhost:8080/health)

## Hello Example URL end-points

### Hello World
[http://localhost:8080/helloworld](http://localhost:8080/helloworld)

### Hello using a name as a path variable
[http://localhost:8080/hello1/tom](http://localhost:8080/hello/tom)

### Hello using a name as a request paramter
[http://localhost:8080/hello2?name=fred](http://localhost:8080/hello2?name=fred)

