package controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import id.co.danamon.dbank.Application;
import id.co.danamon.dbank.common.constant.ApiVersion;
import id.co.danamon.dbank.servicename.service.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class GreetingControllerMockMvcTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9999));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService greetingService;

    @Test
    public void greetAllCatOwner() throws Exception {
        when(greetingService.greetAllCatOwner(any())).thenReturn(new ArrayList<>());
        this.mockMvc.perform(post("/greeting/")
                .characterEncoding("utf-8")
                .contentType(ApiVersion.V1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"gender\": \"WOMAN\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void greetCatOwner() throws Exception {
        when(greetingService.greetCatOwner(any())).thenReturn(Optional.of("Hello"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/greeting/detail")
                .characterEncoding("utf-8")
                .content("{\"id\":1}")
                .contentType(ApiVersion.V1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
