package com.jsimone.controller

import com.jsimone.TestBase
import com.jsimone.error.FieldError
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import org.junit.{Assert, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.{HttpStatus, MediaType}
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JsonResponseControllerTest extends TestBase {

  @Test
  def jsonResponseTest(): Unit = {
    val url = "http://localhost:" + port + "/json"
    val responseEntity = restTemplate.getForEntity(url, classOf[String])
    val expectedResponse =
      """{"name":"john doe","age":18,"hasChild":true,"childs":[{"name":"dorothy","age":5,"hasChild":false},{"name":"bill","age":8,"hasChild":false}]}"""
    assertThat(responseEntity.getBody).isEqualTo(expectedResponse)
    assertThat(responseEntity.getStatusCode.value()).isEqualTo(HttpStatus.OK.value())
    Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders.getContentType)
  }
}

