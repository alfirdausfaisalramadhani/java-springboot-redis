package controller;

import id.co.ist.mobile.Application;
import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.common.constant.GenericResponseKey;
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

import java.util.Optional;

import static id.co.ist.mobile.servicename.constant.ServiceResponseKey.NOT_FOUND;
import static id.co.ist.mobile.servicename.constant.ServiceResponseKey.PARAMETER_INVALID;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class GreetingControllerIT_GreetCatOwnerTest {

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
    public void greetCatOwnerTestSuccess() {
        Mockito.when(greetingService.greetCatOwner(any()))
                .thenReturn(Optional.of("Hello"));

        given()
                .body("{\n" +
                        "    \"id\": 1\n" +
                        "}")
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/greeting/detail")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("response_key", Matchers.containsString(GenericResponseKey.SUCCESS.getResponseKey()))
                .body("message.title_eng", Matchers.containsString(GenericResponseKey.SUCCESS.getResponseKey()))
                .body("message.desc_eng", Matchers.containsString(GenericResponseKey.SUCCESS.getResponseKey()))
                .body("data", Matchers.isA(String.class));
    }

    @Test
    public void greetCatOwnerTestParameterInvalid() {
        given()
                .body("{}")
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/greeting/detail")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("response_key", Matchers.containsString(PARAMETER_INVALID));
    }

    @Test
    public void greetCatOwnerTestNotFound() {
        Mockito.when(greetingService.greetCatOwner(any()))
                .thenReturn(Optional.empty());

        given()
                .body("{\n" +
                        "    \"id\": 1\n" +
                        "}")
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/greeting/detail")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("response_key", Matchers.containsString(NOT_FOUND));
    }


    @Test
    public void greetCatOwnerTestUnknownException() {
        Mockito.when(greetingService.greetCatOwner(any()))
                .thenThrow(new NullPointerException());

        given()
                .body("{\n" +
                        "    \"id\": 1\n" +
                        "}")
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/greeting/detail")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("response_key", Matchers.containsString(GenericResponseKey.UNKNOWN_ERROR.getResponseKey()));
    }
}
