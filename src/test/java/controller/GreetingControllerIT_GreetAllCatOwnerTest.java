package controller;

import id.co.ist.mobile.Application;
import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.common.constant.GenericResponseKey;
import id.co.ist.mobile.servicename.domain.dto.internal.GreetingRequest;
import id.co.ist.mobile.servicename.service.GreetingService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import wiremock.org.apache.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class GreetingControllerIT_GreetAllCatOwnerTest {

    @Value("${server.servlet.context-path}")
    private String serviceNamePrefix;

    @LocalServerPort
    private int port;

    @MockBean
    private GreetingService greetingService;

    @Before
    public void beforeEachTest() {
        RestAssured.port = this.port;
    }

    @Test
    public void greetAllCatOwner() {
        Mockito.when(greetingService.greetAllCatOwner(any()))
                .thenReturn(List.of("Hello User", "Hello User2"));
        given()
                .contentType(ApiVersion.V1)
                .body(new GreetingRequest())
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/greeting/")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("response_key", Matchers.containsStringIgnoringCase(GenericResponseKey.SUCCESS.getResponseKey()))
                .body("message.title_eng", Matchers.containsStringIgnoringCase(GenericResponseKey.SUCCESS.getResponseKey()))
                .body("message.desc_eng", Matchers.containsStringIgnoringCase(GenericResponseKey.SUCCESS.getResponseKey()))
                .body("data.greeting_list", Matchers.isA(List.class))
                .body("data.greeting_list[0]", Matchers.containsStringIgnoringCase("Hello"));
    }

    @Test
    public void greetAllCatOwnerUnknownError() {
        Mockito.when(greetingService.greetAllCatOwner(any()))
                .thenThrow(new NullPointerException());
        given()
                .body(new GreetingRequest())
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/greeting/")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("response_key", Matchers.containsStringIgnoringCase(GenericResponseKey.UNKNOWN_ERROR.getResponseKey()))
        ;
    }

}
