package controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import id.co.ist.mobile.Application;
import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.servicename.domain.dto.external.CatFactDto;
import id.co.ist.mobile.servicename.domain.dto.internal.CatOwnerDto;
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

import java.util.Optional;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CatControllerMockMvcTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9999));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    @Test
    public void getCatFactTest() throws Exception {
        Mockito.when(catService.getCatFact()).thenReturn(new CatFactDto());
        this.mockMvc.perform(post("/cat/fact/")
                .content("{}")
                .characterEncoding("utf-8")
                .contentType(ApiVersion.V1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createCatOwnerTest() throws Exception {
        Mockito.when(catService.createCatOwner(any())).thenReturn(new CatOwnerDto());
        this.mockMvc.perform(post("/cat/owner/create")
                .characterEncoding("utf-8")
                .contentType(ApiVersion.V1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"gender\": \"WOMAN\",\n" +
                        "    \"first_name\": \"RISKA\",\n" +
                        "    \"last_name\": \"RISKA\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void deleteCatOwnerTest() throws Exception {
        Mockito.when(catService.deleteCatOwner(any())).thenReturn(Optional.of(new CatOwnerDto()));
        this.mockMvc.perform(post("/cat/owner/delete")
                .characterEncoding("utf-8")
                .contentType(ApiVersion.V1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"gender\": \"WOMAN\",\n" +
                        "    \"first_name\": \"RISKA\",\n" +
                        "    \"last_name\": \"RISKA\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updateCatOwnerTest() throws Exception {
        Mockito.when(catService.updateCatOwner(any())).thenReturn(Optional.of(new CatOwnerDto()));
        this.mockMvc.perform(post("/cat/owner/update")
                .characterEncoding("utf-8")
                .contentType(ApiVersion.V1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"gender\": \"WOMAN\",\n" +
                        "    \"first_name\": \"RISKA\",\n" +
                        "    \"last_name\": \"RISKA\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
