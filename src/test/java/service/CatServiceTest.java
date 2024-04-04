package service;

import id.co.ist.mobile.Application;
import id.co.ist.mobile.common.util.MapperUtil;
import id.co.ist.mobile.servicename.constant.Gender;
import id.co.ist.mobile.servicename.domain.dao.CatOwner;
import id.co.ist.mobile.servicename.domain.dto.external.CatFactDto;
import id.co.ist.mobile.servicename.domain.dto.internal.CatOwnerDto;
import id.co.ist.mobile.servicename.repository.CatOwnerRepository;
import id.co.ist.mobile.servicename.service.CatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CatServiceTest {

    @Autowired
    private CatService catService;

    @Autowired
    private CatOwnerRepository repository;

    @MockBean
    private RestTemplate restTemplate;

    @Before
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void getCatFactTest() {
        CatFactDto expected = new CatFactDto();
        when(restTemplate.getForEntity(anyString(), any()))
                .thenReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        CatFactDto actual = catService.getCatFact();
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(restTemplate, times(1))
                .getForEntity(anyString(), any());
    }

    @Test
    public void createCatOwnerTest() {
        CatOwnerDto catOwnerDto = CatOwnerDto.builder().gender(Gender.WOMAN).firstName("riska").lastName("wijaya").build();
        catService.createCatOwner(catOwnerDto);

        List<CatOwner> catOwnerList = repository.findAll();
        assertEquals(1, catOwnerList.size());

        assertNotNull(catOwnerList.get(0).getId());
        assertEquals(catOwnerDto.getGender(), catOwnerList.get(0).getGender());
        assertEquals(catOwnerDto.getFirstName(), catOwnerList.get(0).getFirstName());
        assertEquals(catOwnerDto.getLastName(), catOwnerList.get(0).getLastName());

    }

    @Test
    public void updateCatOwnerTest() {
        CatOwnerDto catOwnerDto = CatOwnerDto.builder().id(1l).gender(Gender.WOMAN).firstName("riska").lastName("wijaya").build();
        assertTrue(catService.updateCatOwner(catOwnerDto).isEmpty());

        CatOwner catOwner = repository.saveAndFlush((CatOwner) MapperUtil.transform(catOwnerDto, new CatOwner(), true));
        catOwnerDto.setId(catOwner.getId());
        assertTrue(catService.updateCatOwner(catOwnerDto).isPresent());
    }

    @Test
    public void deleteCatOwnerTest() {
        assertTrue(CollectionUtils.isEmpty(repository.findAll()));

        CatOwnerDto catOwnerDto = CatOwnerDto.builder().id(1l).gender(Gender.WOMAN).firstName("riska").lastName("wijaya").build();
        CatOwner catOwner = repository.saveAndFlush((CatOwner) MapperUtil.transform(catOwnerDto, new CatOwner(), true));
        assertFalse(CollectionUtils.isEmpty(repository.findAll()));

        catService.deleteCatOwner(catOwner.getId());
        assertTrue(CollectionUtils.isEmpty(repository.findAll()));

    }

}
