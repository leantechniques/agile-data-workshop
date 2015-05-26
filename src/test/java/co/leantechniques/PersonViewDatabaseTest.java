package co.leantechniques;

import co.leantechniques.model.Person;
import co.leantechniques.model.Phone;
import co.leantechniques.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-contexts/test-context.xml")
public class PersonViewDatabaseTest {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PersonRepository personRepository;

    @Before
    public void setUp(){

        jdbcTemplate.execute("delete from email");
        jdbcTemplate.execute("delete from phone_numbers");
        jdbcTemplate.execute("delete from people");

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        Phone homePhone1 = new Phone("555-111-1111", "H", person1);
        person1.setAddressLine1("123 Main");
        person1.setAddressLine2("Apt #2");
        person1.setCity("Des Moines");
        person1.setState("IA");
        person1.setZipCode("50265");
        person1.addPhone(homePhone1);
        personRepository.save(person1);


        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Smith");
        Phone homePhone2 = new Phone("555-222-2222", "H", person2);
        person2.setAddressLine1("789 Spruce");
//        person1.setAddressLine2(null);
        person2.setCity("De Soto");
        person2.setState("IA");
        person2.setZipCode("50069");
        person2.addPhone(homePhone2);
        personRepository.save(person2);




    }


    @Test
    public void backwardsCompatibility() {
        PersonViewResultSetExtractor personView = new PersonViewResultSetExtractor();
        List<PersonView> people = jdbcTemplate.query("select * from people_view", personView);

        assertEquals(2, people.size());

        assertEquals(
                "PersonView{first_name='John', last_name='Doe', phone_number='555-111-1111', address_line_1='123 Main', address_line_2='Apt #2', city='Des Moines', state='IA', zip_code='50265'}",
                people.get(0).toString());

        assertEquals(
                "PersonView{first_name='Jane', last_name='Smith', phone_number='555-222-2222', address_line_1='789 Spruce', address_line_2='null', city='De Soto', state='IA', zip_code='50069'}",
                people.get(1).toString());
    }


    private static class PersonViewResultSetExtractor implements ResultSetExtractor<List<PersonView>> {

        @Override
        public List<PersonView> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<PersonView> results = new ArrayList<PersonView>();
            while (resultSet.next()) {
                PersonView personView = new PersonView();
                personView.person_id = resultSet.getString("person_id");
                personView.first_name = resultSet.getString("first_name");
                personView.last_name = resultSet.getString("last_name");
                personView.phone_number = resultSet.getString("phone_number");
                personView.address_line_1 = resultSet.getString("address_line_1");
                personView.address_line_2 = resultSet.getString("address_line_2");
                personView.city = resultSet.getString("city");
                personView.state = resultSet.getString("state");
                personView.zip_code = resultSet.getString("zip_code");
                results.add(personView);
            }
            return results;
        }


        private void extract(ResultSet resultSet, Map<String, String> map, String columnLabel) throws SQLException {
            map.put(columnLabel, resultSet.getString(columnLabel));
        }

    }

    private static class PersonView {
        private String person_id;
        private String first_name;
        private String last_name;
        private String phone_number;
        private String address_line_1;
        private String address_line_2;
        private String city;
        private String state;
        private String zip_code;

        @Override
        public String toString() {
            return "PersonView{" +
                   "first_name='" + first_name + '\'' +
                   ", last_name='" + last_name + '\'' +
                   ", phone_number='" + phone_number + '\'' +
                   ", address_line_1='" + address_line_1 + '\'' +
                   ", address_line_2='" + address_line_2 + '\'' +
                   ", city='" + city + '\'' +
                   ", state='" + state + '\'' +
                   ", zip_code='" + zip_code + '\'' +
                   '}';
        }
    }

}
