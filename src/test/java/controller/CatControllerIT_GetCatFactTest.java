package controller;

import id.co.danamon.dbank.Application;
import id.co.danamon.dbank.common.constant.ApiVersion;
import id.co.danamon.dbank.common.constant.GenericResponseKey;
import id.co.danamon.dbank.common.constant.SourceSystem;
import id.co.danamon.dbank.common.domain.ApiResponse;
import id.co.danamon.dbank.common.exception.MobileRestException;
import id.co.danamon.dbank.servicename.domain.dto.external.CatFactDto;
import id.co.danamon.dbank.servicename.service.CatService;
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
import org.springframework.web.client.ResourceAccessException;
import wiremock.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class CatControllerIT_GetCatFactTest {

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
    public void getCatFactSuccess() {
        Mockito.when(catService.getCatFact())
                .thenReturn(new CatFactDto());
        given()
                .body("{}")
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/cat/fact/")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("response_key", Matchers.containsStringIgnoringCase(GenericResponseKey.SUCCESS.getResponseKey()))
                .body("message.title_eng", Matchers.containsStringIgnoringCase(GenericResponseKey.SUCCESS.getResponseKey()))
                .body("message.desc_eng", Matchers.containsStringIgnoringCase(GenericResponseKey.SUCCESS.getResponseKey()));
    }

    @Test
    public void getCatFactGatewayTimeout() {
        Mockito.when(catService.getCatFact())
                .thenThrow(new ResourceAccessException(""));
        given()
                .body("{}")
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/cat/fact/")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_GATEWAY_TIMEOUT)
                .body("response_key", Matchers.notNullValue());
    }

    @Test
    public void getCatFactRestClientError() {
        Mockito.when(catService.getCatFact())
                .thenThrow(new MobileRestException(new ApiResponse(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR));
        given()
                .body("{}")
                .contentType(ApiVersion.V1)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .post(serviceNamePrefix + "/cat/fact/")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("source_system", Matchers.containsString(SourceSystem.DBANKPRO));
    }

}
