package co.leantechniques.repository;

import co.leantechniques.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-contexts/test-context.xml")
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate.execute("delete from email");
        jdbcTemplate.execute("delete from phone_numbers");
        jdbcTemplate.execute("delete from people");
    }

    @Test
    public void saveFindDelete() {
        deleteAll();
        Assert.assertTrue(repository.findAll().isEmpty());
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        repository.save(person);
        Person persistedPerson = repository.findAll().get(0);
        assertEquals("John", persistedPerson.getFirstName());
        assertEquals("Doe", persistedPerson.getLastName());
        deleteAll();
        Assert.assertTrue(repository.findAll().isEmpty());
    }

    private void deleteAll() {
        for (Person person : repository.findAll()) {
            repository.delete(person);
        }
    }
}
