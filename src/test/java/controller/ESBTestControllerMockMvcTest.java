package controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import id.co.ist.mobile.Application;
import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.servicename.domain.dto.external.accountdetailinquiry.CIMBAcctInqSvc;
import id.co.ist.mobile.servicename.service.ESBService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ESBTestControllerMockMvcTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9999));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ESBService esbService;

    @Test
    public void getCatFactTest() throws Exception {
        Mockito.when(esbService.getAccountInquiryDetail(anyString(), anyString())).thenReturn(new CIMBAcctInqSvc());
        this.mockMvc.perform(post("/esb/test")
                .content("{}")
                .characterEncoding("utf-8")
                .contentType(ApiVersion.V1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

 }
