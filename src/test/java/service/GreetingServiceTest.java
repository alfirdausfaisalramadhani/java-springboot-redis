package service;

import id.co.danamon.dbank.Application;
import id.co.danamon.dbank.servicename.constant.Gender;
import id.co.danamon.dbank.servicename.domain.dao.CatOwner;
import id.co.danamon.dbank.servicename.domain.dto.internal.GreetingRequest;
import id.co.danamon.dbank.servicename.repository.CatOwnerRepository;
import id.co.danamon.dbank.servicename.service.GreetingService;
import id.co.danamon.dbank.servicename.util.GreetingUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GreetingServiceTest {

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private CatOwnerRepository catOwnerRepository;

    @Before
    public void beforeEach() {
        catOwnerRepository.deleteAll();
    }

    public List<String> getGreeting(GreetingRequest requestDto) {

        List<CatOwner> catOwners =
                catOwnerRepository.findByGender(requestDto.getGender());

        return catOwners.stream()
                .map(owner -> GreetingUtil.constructGreeting(owner.getGender(), owner.getFirstName()))
                .collect(Collectors.toList());
    }

    @Test
    public void greetAllCatOwner() {

        catOwnerRepository.save(CatOwner.builder().gender(Gender.WOMAN).firstName("my name").lastName("1").build());
        catOwnerRepository.save(CatOwner.builder().gender(Gender.MAN).firstName("my name").lastName("2").build());

        List<String> listGreetingWOMAN = greetingService.greetAllCatOwner(GreetingRequest.builder().gender(Gender.WOMAN).build());

        assertFalse(CollectionUtils.isEmpty(listGreetingWOMAN));
        assertEquals(1, listGreetingWOMAN.size());
        assertTrue(listGreetingWOMAN.get(0).contains("Ms"));

        List<String> listGreetingAll = greetingService.greetAllCatOwner(new GreetingRequest());
        assertFalse(CollectionUtils.isEmpty(listGreetingAll));
        assertEquals(2, listGreetingAll.size());
    }

    @Test
    public void greetCatOwner() {
        catOwnerRepository.save(CatOwner.builder().gender(Gender.WOMAN).firstName("my name").lastName("1").build());
        catOwnerRepository.save(CatOwner.builder().gender(Gender.WOMAN).firstName("my name").lastName("2").build());

        List<CatOwner> catOwnerList = catOwnerRepository.findAll();

        Optional<String> greetingOptional = greetingService.greetCatOwner(catOwnerList.get(0).getId());

        assertFalse(greetingOptional.isEmpty());

        Optional<String> greetingOptional2 = greetingService.greetCatOwner(999l);

        assertTrue(greetingOptional2.isEmpty());
    }

}
