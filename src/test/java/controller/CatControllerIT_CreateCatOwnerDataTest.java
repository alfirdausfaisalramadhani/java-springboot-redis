package controller;

import id.co.ist.mobile.Application;
import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.common.constant.GenericResponseKey;
import id.co.ist.mobile.servicename.constant.Gender;
import id.co.ist.mobile.servicename.domain.dto.internal.CatOwnerDto;
import id.co.ist.mobile.servicename.service.CatService;
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

import static id.co.ist.mobile.servicename.constant.ServiceResponseKey.PARAMETER_INVALID;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class CatControllerIT_CreateCatOwnerDataTest {

    @Value("${server.servlet.context-path}")
    private String serviceNamePrefix;

    @LocalServerPort
    private int port;

    @MockBean
    private CatService catService;

    @Before
    public void beforeEachTest() {
        RestAssured.port = this.port;
    }


    @Test
    public void createCatOwnerDataSuccess() {
        Mockito.when(catService.createCatOwner(any())).thenReturn(new CatOwnerDto());
        given()
                .body(new CatOwnerDto(1l, "riska", "riska", Gender.WOMAN))
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/cat/owner/create")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("response_key", Matchers.is(GenericResponseKey.SUCCESS.getResponseKey()));
    }

    @Test
    public void createCatOwnerParameterInvalid() {
        given()
                .body(new CatOwnerDto())
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/cat/owner/create")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("response_key", Matchers.is(PARAMETER_INVALID));
    }

    @Test
    public void createCatOwnerUnknownError() {
        Mockito.when(catService.createCatOwner(any())).thenThrow(new NullPointerException());
        given()
                .body(new CatOwnerDto(1l, "riska", "riska", Gender.WOMAN))
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/cat/owner/create")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("response_key", Matchers.is(GenericResponseKey.UNKNOWN_ERROR.getResponseKey()));
    }
}
