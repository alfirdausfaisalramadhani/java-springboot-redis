package repository;

import id.co.ist.mobile.Application;
import id.co.ist.mobile.servicename.constant.Gender;
import id.co.ist.mobile.servicename.domain.dao.CatOwner;
import id.co.ist.mobile.servicename.repository.CatOwnerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CatOwnerRepositoryTest {

    @Autowired
    private CatOwnerRepository repository;

    @Before
    public void beforeEachTest() {
        repository.deleteAll();
        repository.save(CatOwner.builder().gender(Gender.MAN).firstName("first name").lastName("last name").build());
    }

    @Test
    public void test() {
        assertEquals(1, repository.findAll().spliterator().estimateSize());
    }

    @Test
    public void findByValue_Found() {
        List<CatOwner> actual = repository.findByGender(Gender.MAN);
        assertFalse(actual.isEmpty());
    }

    @Test
    public void findByValue_NotFound() {
        List<CatOwner> actual = repository.findByGender(Gender.WOMAN);
        assertTrue(actual.isEmpty());
    }
}
