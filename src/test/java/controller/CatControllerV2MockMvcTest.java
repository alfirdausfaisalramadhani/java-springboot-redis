package controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import id.co.ist.mobile.Application;
import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.servicename.domain.dto.external.CatFactDto;
import id.co.ist.mobile.servicename.service.CatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CatControllerV2MockMvcTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9999));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    @Test
    public void getCatFact() throws Exception {
        Mockito.when(catService.getCatFact()).thenReturn(new CatFactDto());
        this.mockMvc.perform(post("/cat/fact/")
                .content("{}")
                .characterEncoding("utf-8")
                .contentType(ApiVersion.V2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
